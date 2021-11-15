package com.toybethsystems.dokto.twilio.data.sdk

import android.content.Context
import com.toybethsystems.dokto.twilio.data.sdk.ConnectOptionsFactory
import com.twilio.video.Room
import com.twilio.video.Video

class VideoClient(
    private val context: Context,
    private val connectOptionsFactory: ConnectOptionsFactory
) {

    suspend fun connect(
        identity: String,
        roomName: String,
        roomListener: Room.Listener
    ): Room {

            return Video.connect(
                    context,
                    connectOptionsFactory.newInstance(identity, roomName),
                    roomListener)
    }
}
