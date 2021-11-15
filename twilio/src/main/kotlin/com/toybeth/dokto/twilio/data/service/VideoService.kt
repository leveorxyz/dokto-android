package com.toybeth.dokto.twilio.data.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.orhanobut.logger.Logger
import com.toybeth.dokto.twilio.data.sdk.RoomManager
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

private const val ROOM_NAME_EXTRA = "ROOM_NAME_EXTRA"

@AndroidEntryPoint
class VideoService(
    private val rxDisposables: CompositeDisposable = CompositeDisposable()
) : Service() {

    companion object {
        fun startService(context: Context, roomName: String) {
            Intent(context, VideoService::class.java).let { intent ->
                intent.putExtra(ROOM_NAME_EXTRA, roomName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            }
        }

        fun stopService(context: Context) {
            Intent(context, VideoService::class.java).let { context.stopService(it) }
        }
    }

    @Inject
    lateinit var roomManager: RoomManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        setupForegroundService(intent)
        Logger.d("VideoService created")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("VideoService destroyed")
        rxDisposables.clear()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setupForegroundService(intent: Intent?) {
        intent?.let { it.getStringExtra(ROOM_NAME_EXTRA)?.let { roomName ->
            val roomNotification = RoomNotification(this@VideoService)
            startForeground(
                    ONGOING_NOTIFICATION_ID,
                    roomNotification.buildNotification(roomName))
        } }
    }
}
