package com.toybeth.dokto.twilio.ui.call

import UriWrapper
import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.Logger
import com.toybeth.dokto.twilio.R
import com.toybeth.dokto.twilio.data.preferences.TwilioSharedPreference
import com.toybeth.dokto.twilio.databinding.ActivityTwilioCallBinding
import com.toybeth.dokto.twilio.ui.common.TwilioCallViewModel
import com.twilio.audioswitch.AudioDevice
import dagger.hilt.android.AndroidEntryPoint
import io.uniflow.android.livedata.onEvents
import io.uniflow.android.livedata.onStates
import javax.inject.Inject
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.graphics.Point
import android.util.Rational
import android.view.*


@AndroidEntryPoint
class TwilioCallActivity : AppCompatActivity() {

    private var savedVolumeControlStream = 0
    private var displayName: String? = null
    private var localParticipantSid = LOCAL_PARTICIPANT_STUB_SID
    private lateinit var statsListAdapter: StatsListAdapter

    /** Coordinates participant thumbs and primary participant rendering.  */
    private lateinit var primaryParticipantController: PrimaryParticipantController
    private lateinit var participantAdapter: ParticipantAdapter
    private lateinit var recordingAnimation: ObjectAnimator
    private var canEnterPictureInPictureMode = false

    @Inject
    lateinit var sharedPreferences: TwilioSharedPreference
    private lateinit var binding: ActivityTwilioCallBinding

    private val viewModel: TwilioCallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTwilioCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.joinRoom.roomName.doOnTextChanged { text: CharSequence?, _, _, _ ->
            roomNameTextChanged(text)
        }
        binding.joinRoom.connect.setOnClickListener { connectButtonClick() }
        binding.disconnect.setOnClickListener { disconnectButtonClick() }
        binding.localVideo.setOnClickListener { toggleLocalVideo() }
        binding.localAudio.setOnClickListener { toggleLocalAudio() }
        binding.startShareScreen.setOnClickListener { requestScreenCapturePermission() }
        binding.stopShareScreen.setOnClickListener { viewModel.processInput(RoomViewEvent.StopScreenCapture) }
        
        // So calls can be answered when screen is locked
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        // Grab views
        setupThumbnailRecyclerView()

        // Cache volume control stream
        savedVolumeControlStream = volumeControlStream

        // Setup participant controller
        primaryParticipantController = PrimaryParticipantController(binding.room.primaryVideo)

        onStates(viewModel) { state ->
            if (state is RoomViewState) bindRoomViewState(state)
        }
        onEvents(viewModel) { event ->
            if (event is RoomViewEffect) bindRoomViewEffects(event)
        }

        setupRecordingAnimation()
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        if(isInPictureInPictureMode) {
            binding.controlButtons.visibility = View.GONE
            binding.room.remoteVideoThumbnails.visibility = View.GONE
        } else {
            binding.controlButtons.visibility = View.VISIBLE
            binding.room.remoteVideoThumbnails.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recordingAnimation.cancel()
    }

    override fun onStart() {
        super.onStart()
        checkIntentURI()
    }

    override fun onResume() {
        super.onResume()
        displayName = "shafayat1"
        setTitle(displayName)
        viewModel.processInput(RoomViewEvent.OnResume)
    }

    override fun onPause() {
        super.onPause()
        if(canEnterPictureInPictureMode) {
            enterPipMode()
        }
        viewModel.processInput(RoomViewEvent.OnPause)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            val recordAudioPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
            val cameraPermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED
            if (recordAudioPermissionGranted && cameraPermissionGranted) {
                viewModel.processInput(RoomViewEvent.OnResume)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.switch_camera_menu_item -> {
                viewModel.processInput(RoomViewEvent.SwitchCamera)
                true
            }
            R.id.share_screen_menu_item -> {
                if (item.title == getString(R.string.share_screen)) {
                    requestScreenCapturePermission()
                } else {
                    viewModel.processInput(RoomViewEvent.StopScreenCapture)
                }
                true
            }
            R.id.device_menu_item -> {
                displayAudioDeviceList()
                true
            }
            R.id.pause_audio_menu_item -> {
                if (item.title == getString(R.string.pause_audio))
                    viewModel.processInput(RoomViewEvent.DisableLocalAudio)
                else
                    viewModel.processInput(RoomViewEvent.EnableLocalAudio)
                true
            }
            R.id.pause_video_menu_item -> {
                if (item.title == getString(R.string.pause_video))
                    viewModel.processInput(RoomViewEvent.DisableLocalVideo)
                else
                    viewModel.processInput(RoomViewEvent.EnableLocalVideo)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MEDIA_PROJECTION_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Snackbar.make(
                    binding.room.primaryVideo,
                    R.string.screen_capture_permission_not_granted,
                    BaseTransientBottomBar.LENGTH_LONG
                )
                    .show()
                return
            }
            data?.let { data ->
                viewModel.processInput(RoomViewEvent.StartScreenCapture(resultCode, data))
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(!canEnterPictureInPictureMode) {
            viewModel.processInput(RoomViewEvent.Disconnect)
        }
    }

    private fun enterPipMode() {
        val d: Display = windowManager
            .defaultDisplay
        val p = Point()
        d.getSize(p)
        val width: Int = p.x
        val height: Int = p.y

        val ratio = Rational(width, height)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val pipBuilder = PictureInPictureParams.Builder()
                pipBuilder
                    .setAspectRatio(ratio)
                    .build()
                enterPictureInPictureMode(pipBuilder.build())
            } else {
                enterPictureInPictureMode()
            }
        }
    }

    private fun setupRecordingAnimation() {
        val recordingDrawable = ContextCompat.getDrawable(this, R.drawable.ic_recording)
        recordingAnimation = ObjectAnimator.ofPropertyValuesHolder(
            recordingDrawable,
            PropertyValuesHolder.ofInt("alpha", 100, 255)
        ).apply {
            target = recordingDrawable
            duration = 750
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
        binding.recordingIndicator.setCompoundDrawablesWithIntrinsicBounds(
            recordingDrawable, null, null, null
        )
    }

    private fun checkIntentURI(): Boolean {
        var isAppLinkProvided = false
        val uri = intent.data
        val roomName = UriRoomParser(UriWrapper(uri)).parseRoom()
        if (roomName != null) {
            binding.joinRoom.roomName.setText(roomName)
            isAppLinkProvided = true
        }
        return isAppLinkProvided
    }

    private fun setupThumbnailRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.room.remoteVideoThumbnails.layoutManager = layoutManager
        participantAdapter = ParticipantAdapter()
        participantAdapter
            .viewHolderEvents
            .observe(this, { viewEvent: RoomViewEvent -> viewModel.processInput(viewEvent) })
        binding.room.remoteVideoThumbnails.adapter = participantAdapter
    }

    private fun roomNameTextChanged(text: CharSequence?) {
        binding.joinRoom.connect.isEnabled = !TextUtils.isEmpty(text)
    }

    private fun connectButtonClick() {
        InputUtils.hideKeyboard(this)
        binding.joinRoom.connect.isEnabled = false
        // obtain room name
        val text = binding.joinRoom.roomName.text
        if (text != null) {
            val roomName = text.toString()
            val viewEvent = RoomViewEvent.Connect(displayName ?: "", roomName)
            viewModel.processInput(viewEvent)
        }
    }

    private fun disconnectButtonClick() {
        viewModel.processInput(RoomViewEvent.Disconnect)
        // TODO Handle screen share
    }

    private fun toggleLocalVideo() {
        viewModel.processInput(RoomViewEvent.ToggleLocalVideo)
    }

    private fun toggleLocalAudio() {
        viewModel.processInput(RoomViewEvent.ToggleLocalAudio)
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
                ),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun updateLayout(roomViewState: RoomViewState) {
        var disconnectButtonState = View.GONE
        var joinRoomLayoutState = View.VISIBLE
        var joinStatusLayoutState = View.GONE
        var settingsMenuItemState = true
        var screenCaptureMenuItemState = false
        val roomEditable = binding.joinRoom.roomName.text
        val isRoomTextNotEmpty = roomEditable != null && roomEditable.toString().isNotEmpty()
        var connectButtonEnabled = isRoomTextNotEmpty
        var roomName = displayName
        var toolbarTitle = displayName
        var joinStatus = ""
        var recordingWarningVisibility = View.GONE
        canEnterPictureInPictureMode = false
        when (roomViewState.configuration) {
            RoomViewConfiguration.Connecting -> {
                canEnterPictureInPictureMode = true
                disconnectButtonState = View.VISIBLE
                joinRoomLayoutState = View.GONE
                joinStatusLayoutState = View.VISIBLE
                recordingWarningVisibility = View.VISIBLE
                settingsMenuItemState = false
                connectButtonEnabled = false
                if (roomEditable != null) {
                    roomName = roomEditable.toString()
                }
                joinStatus = "Joining..."
                binding.shareScreen.visibility = View.GONE
            }
            RoomViewConfiguration.Connected -> {
                canEnterPictureInPictureMode = true
                disconnectButtonState = View.VISIBLE
                joinRoomLayoutState = View.GONE
                joinStatusLayoutState = View.GONE
                settingsMenuItemState = false
                screenCaptureMenuItemState = true
                connectButtonEnabled = false
                roomName = roomViewState.title
                toolbarTitle = roomName
                joinStatus = ""
                binding.recordingIndicator.visibility =
                    if (roomViewState.isRecording) View.VISIBLE else View.GONE
                binding.shareScreen.visibility = View.VISIBLE
            }
            RoomViewConfiguration.Lobby -> {
                canEnterPictureInPictureMode = false
                connectButtonEnabled = isRoomTextNotEmpty
                screenCaptureMenuItemState = false
                binding.recordingIndicator.visibility = View.GONE
                binding.shareScreen.visibility = View.GONE
            }
        }
        val isMicEnabled = roomViewState.isMicEnabled
        val isCameraEnabled = roomViewState.isCameraEnabled
        val isLocalMediaEnabled = isMicEnabled && isCameraEnabled
        binding.localAudio.isEnabled = isLocalMediaEnabled
        binding.localVideo.isEnabled = isLocalMediaEnabled
        val micDrawable =
            if (roomViewState.isAudioMuted || !isLocalMediaEnabled) R.drawable.ic_mic_off_gray_24px else R.drawable.ic_mic_white_24px
        val videoDrawable =
            if (roomViewState.isVideoOff || !isLocalMediaEnabled) R.drawable.ic_videocam_off_gray_24px else R.drawable.ic_videocam_white_24px
        binding.localAudio.setImageResource(micDrawable)
        binding.localVideo.setImageResource(videoDrawable)
        statsListAdapter = StatsListAdapter(this)
        binding.statsRecyclerView.adapter = statsListAdapter
        binding.statsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.disconnect.visibility = disconnectButtonState
        binding.joinRoom.joinRoomLayout.visibility = joinRoomLayoutState
        binding.joinStatusLayout.visibility = joinStatusLayoutState
        binding.joinRoom.connect.isEnabled = connectButtonEnabled
        setTitle(toolbarTitle)
        binding.joinStatus.text = joinStatus
        binding.joinRoomName.text = roomName
        binding.recordingNotice.visibility = recordingWarningVisibility

        // TODO: Remove when we use a Service to obtainTokenAndConnect to a room
        val screenCaptureResources = if (roomViewState.isScreenCaptureOn) {
            binding.startShareScreen.visibility = View.GONE
            binding.stopShareScreen.visibility = View.VISIBLE
        } else {
            binding.startShareScreen.visibility = View.VISIBLE
            binding.stopShareScreen.visibility = View.GONE
        }
    }

    private fun setTitle(toolbarTitle: String?) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = toolbarTitle
        }
    }

    private fun setVolumeControl(setVolumeControl: Boolean) {
        volumeControlStream = if (setVolumeControl) {
            /*
             * Enable changing the volume using the up/down keys during a conversation
             */
            AudioManager.STREAM_VOICE_CALL
        } else {
            savedVolumeControlStream
        }
    }

    @TargetApi(21)
    private fun requestScreenCapturePermission() {
        Logger.d("Requesting permission to capture screen")
        val mediaProjectionManager =
            getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        // This initiates a prompt dialog for the user to confirm screen projection.
        startActivityForResult(
            mediaProjectionManager.createScreenCaptureIntent(), MEDIA_PROJECTION_REQUEST_CODE
        )
    }

    private fun updateStatsUI(roomViewState: RoomViewState) {
        val enableStats = true
        if (enableStats) {
            when (roomViewState.configuration) {
                RoomViewConfiguration.Connected -> {
                    statsListAdapter.updateStatsData(roomViewState.roomStats)
                    binding.statsRecyclerView.visibility = View.VISIBLE
                    binding.statsDisabled.visibility = View.GONE

                    // disable stats if there is room but no participants (no media)
                    val isStreamingMedia = roomViewState.participantThumbnails?.let { thumbnails ->
                        thumbnails.size > 1
                    } ?: false
                    if (!isStreamingMedia) {
                        binding.statsDisabledTitle.text = getString(R.string.stats_unavailable)
                        binding.statsDisabledDescription.text =
                            getString(R.string.stats_description_media_not_shared)
                        binding.statsRecyclerView.visibility = View.GONE
                        binding.statsDisabled.visibility = View.VISIBLE
                    }
                }
                else -> {
                    binding.statsDisabledTitle.text = getString(R.string.stats_unavailable)
                    binding.statsDisabledDescription.text =
                        getString(R.string.stats_description_join_room)
                    binding.statsRecyclerView.visibility = View.GONE
                    binding.statsDisabled.visibility = View.VISIBLE
                }
            }
        } else {
            binding.statsDisabledTitle.text = getString(R.string.stats_gathering_disabled)
            binding.statsDisabledDescription.text = getString(R.string.stats_enable_in_settings)
            binding.statsRecyclerView.visibility = View.GONE
            binding.statsDisabled.visibility = View.VISIBLE
        }
    }

    private fun toggleAudioDevice(enableAudioDevice: Boolean) {
        setVolumeControl(enableAudioDevice)
        val viewEvent =
            if (enableAudioDevice) RoomViewEvent.ActivateAudioDevice else RoomViewEvent.DeactivateAudioDevice
        viewModel.processInput(viewEvent)
    }

    private fun bindRoomViewState(roomViewState: RoomViewState) {
        renderPrimaryView(roomViewState.primaryParticipant)
        renderThumbnails(roomViewState)
        updateLayout(roomViewState)
        updateStatsUI(roomViewState)
    }

    private fun bindRoomViewEffects(roomViewEffect: RoomViewEffect) {
        when (roomViewEffect) {
            is RoomViewEffect.Connected -> {
                toggleAudioDevice(true)
            }
            RoomViewEffect.Disconnected -> {
                localParticipantSid = LOCAL_PARTICIPANT_STUB_SID
                // TODO Update stats
                toggleAudioDevice(false)
            }
            RoomViewEffect.ShowConnectFailureDialog, RoomViewEffect.ShowMaxParticipantFailureDialog -> {
                AlertDialog.Builder(this, R.style.AppTheme_Dialog)
                    .setTitle(getString(R.string.room_screen_connection_failure_title))
                    .setMessage(getConnectFailureMessage(roomViewEffect))
                    .setNeutralButton(getString(android.R.string.ok), null)
                    .show()
                toggleAudioDevice(false)
            }

            RoomViewEffect.PermissionsDenied -> requestPermissions()
        }
    }

    private fun getConnectFailureMessage(roomViewEffect: RoomViewEffect) =
        getString(
            when (roomViewEffect) {
                RoomViewEffect.ShowMaxParticipantFailureDialog -> R.string.room_screen_max_participant_failure_message
                else -> R.string.room_screen_connection_failure_message
            }
        )

//    private fun updateAudioDeviceIcon(selectedAudioDevice: AudioDevice?) {
//        val audioDeviceMenuIcon = when (selectedAudioDevice) {
//            is AudioDevice.BluetoothHeadset -> R.drawable.ic_bluetooth_white_24dp
//            is AudioDevice.WiredHeadset -> R.drawable.ic_headset_mic_white_24dp
//            is AudioDevice.Speakerphone -> R.drawable.ic_volume_up_white_24dp
//            else -> R.drawable.ic_phonelink_ring_white_24dp
//        }
//        this.deviceMenuItem.setIcon(audioDeviceMenuIcon)
//    }

    private fun renderPrimaryView(primaryParticipant: ParticipantViewState) {
        primaryParticipant.run {
            primaryParticipantController.renderAsPrimary(
                if (isLocalParticipant) getString(R.string.you) else identity,
                screenTrack,
                videoTrack,
                isMuted,
                isMirrored
            )
            binding.room.primaryVideo.showIdentityBadge(!primaryParticipant.isLocalParticipant)
        }
    }

    private fun renderThumbnails(roomViewState: RoomViewState) {
        val newThumbnails = if (roomViewState.configuration is RoomViewConfiguration.Connected)
            roomViewState.participantThumbnails else null
        participantAdapter.submitList(newThumbnails)
    }

    private fun displayAudioDeviceList() {
        (viewModel.getState() as RoomViewState).let { viewState ->
            val selectedDevice = viewState.selectedDevice
            val audioDevices = viewState.availableAudioDevices
            if (selectedDevice != null && audioDevices != null) {
                val index = audioDevices.indexOf(selectedDevice)
                val audioDeviceNames = ArrayList<String>()
                for (a in audioDevices) {
                    audioDeviceNames.add(a.name)
                }
                createAudioDeviceDialog(
                    this,
                    index,
                    audioDeviceNames
                ) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    val viewEvent = RoomViewEvent.SelectAudioDevice(audioDevices[i])
                    viewModel.processInput(viewEvent)
                }
                    .show()
            }
        }
    }

    private fun createAudioDeviceDialog(
        activity: Activity,
        currentDevice: Int,
        availableDevices: ArrayList<String>,
        audioDeviceClickListener: DialogInterface.OnClickListener
    ): AlertDialog {
        val builder = AlertDialog.Builder(activity, R.style.AppTheme_Dialog)
        builder.setTitle(activity.getString(R.string.room_screen_select_device))
        builder.setSingleChoiceItems(
            availableDevices.toTypedArray<CharSequence>(),
            currentDevice,
            audioDeviceClickListener
        )
        return builder.create()
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 100
        private const val MEDIA_PROJECTION_REQUEST_CODE = 101

        // This will be used instead of real local participant sid,
        // because that information is unknown until room connection is fully established
        private const val LOCAL_PARTICIPANT_STUB_SID = ""
        fun startActivity(context: Context, appLink: Uri?) {
            val intent = Intent(context, TwilioCallActivity::class.java)
            intent.data = appLink
            context.startActivity(intent)
        }
    }
}