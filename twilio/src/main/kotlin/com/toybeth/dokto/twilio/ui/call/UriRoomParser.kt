package com.toybeth.dokto.twilio.ui.call

import UriWrapper

class UriRoomParser(private val uri: UriWrapper) {

    fun parseRoom(): String? =
            uri.pathSegments?.let {
                if (it.size >= 2) {
                    it[1]
                } else null
            }
}