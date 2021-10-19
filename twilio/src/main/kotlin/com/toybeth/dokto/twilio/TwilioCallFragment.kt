package com.toybeth.dokto.twilio

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.dokto.twilio.databinding.FragmentTwilioCallBinding
import com.twilio.audioswitch.AudioDevice
import com.twilio.audioswitch.AudioSwitch
import com.twilio.video.*
import com.twilio.video.ktx.Video
import com.twilio.video.ktx.createLocalAudioTrack
import com.twilio.video.ktx.createLocalVideoTrack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TwilioCallFragment : BaseViewBindingFragment<TwilioCallViewModel, FragmentTwilioCallBinding>() {

    private val CAMERA_MIC_PERMISSION_REQUEST_CODE = 1
    private lateinit var localVideoView: VideoView

    override val viewModel: TwilioCallViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentTwilioCallBinding
        get() = FragmentTwilioCallBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localVideoView = binding.thumbnailVideoView

        viewModel.mirror.observe(viewLifecycleOwner) {
            moveLocalVideoToPrimaryView(it)
        }

        viewModel.subscribedVideoTrackLiveData.observe(viewLifecycleOwner) {
            addRemoteParticipantVideo(it)
        }

        viewModel.unsubscribedVideoTrackLiveData.observe(viewLifecycleOwner) {
            removeParticipantVideo(it)
        }

        viewModel.localVideoTrackLiveData.observe(viewLifecycleOwner) {
            Logger.d(it?.isEnabled)
            it?.let {
                if(it.isEnabled) {
                    it.addSink(this@TwilioCallFragment.localVideoView)
                    initializeUI()
                }
            }
        }

        viewModel.participantDisconnectedLiveData.observe(viewLifecycleOwner) {
            moveLocalVideoToPrimaryView(viewModel.mirror.value == true)
        }

        viewModel.videoEnabled.observe(viewLifecycleOwner) {
            toggleLocalVideoButton(it)
        }
    }

    override fun onResume() {
        super.onResume()
        requestPermissionForCameraAndMicrophone()
    }

    override fun onPause() {
        Logger.d("STOP")
        viewModel.pause()
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            var cameraAndMicPermissionGranted = true

            for (grantResult in grantResults) {
                cameraAndMicPermissionGranted = cameraAndMicPermissionGranted and
                        (grantResult == PackageManager.PERMISSION_GRANTED)
            }

            if (cameraAndMicPermissionGranted) {
                viewModel.initializeTwilio()
            } else {
                Toast.makeText(
                    requireContext(),
                    R.string.permissions_needed,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA) ||
            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.RECORD_AUDIO
            )
        ) {
            Toast.makeText(
                requireContext(),
                R.string.permissions_needed,
                Toast.LENGTH_LONG
            ).show()
        } else {
            if(viewModel.checkPermissionForCameraAndMicrophone()) {
                viewModel.initializeTwilio()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
                    CAMERA_MIC_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun moveLocalVideoToPrimaryView(mirror: Boolean) {
        localVideoView.mirror = mirror
    }

    private fun addRemoteParticipantVideo(videoTrack: VideoTrack) {
        localVideoView.mirror = false
        videoTrack.addSink(localVideoView)
    }

    private fun removeParticipantVideo(videoTrack: VideoTrack) {
        videoTrack.removeSink(localVideoView)
    }

    private fun initializeUI() {
        binding.connectActionFab.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_video_call_white_24dp
            )
        )
        binding.connectActionFab.show()
//        binding.connectActionFab.setOnClickListener(connectActionClickListener())
        binding.switchCameraActionFab.show()
//        binding.switchCameraActionFab.setOnClickListener(switchCameraClickListener())
        binding.localVideoActionFab.show()
        binding.localVideoActionFab.isClickable = true
        binding.localVideoActionFab.isActivated = true
        binding.localVideoActionFab.setOnClickListener {
            Logger.d("CLICKED")
            viewModel.toggleLocalVideoStream()
        }
        binding.muteActionFab.show()
//        binding.muteActionFab.setOnClickListener(muteClickListener())
    }

    private fun localVideoClickListener(): View.OnClickListener {
        return View.OnClickListener {

        }
    }

    private fun toggleLocalVideoButton(enabled: Boolean) {
        val icon: Int
        if (enabled) {
            icon = R.drawable.ic_videocam_white_24dp
            binding.switchCameraActionFab.show()
        } else {
            icon = R.drawable.ic_videocam_off_black_24dp
            binding.switchCameraActionFab.hide()
        }
        binding.localVideoActionFab.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), icon)
        )
    }
}