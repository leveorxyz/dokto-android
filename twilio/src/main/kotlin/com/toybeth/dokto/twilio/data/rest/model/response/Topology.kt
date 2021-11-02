package com.toybeth.dokto.twilio.data.rest.model.response;

import com.google.gson.annotations.SerializedName

private const val GROUP_ROOM_NAME = "group"
private const val GROUP_SMALL_ROOM_NAME = "group-small"
private const val PEER_TO_PEER_ROOM_NAME = "peer-to-peer"
private const val GO_ROOM_NAME = "go"

enum class Topology(val value: String) {
    @SerializedName(GROUP_ROOM_NAME) GROUP(GROUP_ROOM_NAME),
    @SerializedName(GROUP_SMALL_ROOM_NAME) GROUP_SMALL(GROUP_SMALL_ROOM_NAME),
    @SerializedName(PEER_TO_PEER_ROOM_NAME) PEER_TO_PEER(PEER_TO_PEER_ROOM_NAME),
    @SerializedName(GO_ROOM_NAME) GO(GO_ROOM_NAME);

    override fun toString() = value
}