package com.toybeth.dokto.twilio.data

import android.content.Context
import com.orhanobut.logger.Logger
import com.twilio.conversations.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class TwilioChatRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var conversationsClient: ConversationsClient? = null

    private val mConversationsClientListener: ConversationsClientListener =
        object : ConversationsClientListener {
            override fun onConversationAdded(conversation: Conversation) {}
            override fun onConversationUpdated(
                conversation: Conversation,
                updateReason: Conversation.UpdateReason
            ) {
            }

            override fun onConversationDeleted(conversation: Conversation) {}
            override fun onConversationSynchronizationChange(conversation: Conversation) {}
            override fun onError(errorInfo: ErrorInfo?) {}
            override fun onUserUpdated(user: User?, updateReason: User.UpdateReason?) {}
            override fun onUserSubscribed(user: User?) {}
            override fun onUserUnsubscribed(user: User?) {}
            override fun onClientSynchronization(synchronizationStatus: ConversationsClient.SynchronizationStatus) {
                if (synchronizationStatus == ConversationsClient.SynchronizationStatus.COMPLETED) {
                    loadChannels()
                }
            }

            override fun onNewMessageNotification(s: String, s1: String, l: Long) {}
            override fun onAddedToConversationNotification(s: String) {}
            override fun onRemovedFromConversationNotification(s: String) {}
            override fun onNotificationSubscribed() {}
            override fun onNotificationFailed(errorInfo: ErrorInfo?) {}
            override fun onConnectionStateChange(connectionState: ConversationsClient.ConnectionState) {}
            override fun onTokenExpired() {}
            override fun onTokenAboutToExpire() {
                retrieveToken()
            }
        }

    private fun retrieveToken() {
//        TODO("RETRIEVE TOKEN FROM BACKEND")
    }

    private fun initializeWithAccessToken(token: String) {
        val props = ConversationsClient.Properties.newBuilder().createProperties()
        ConversationsClient.create(
            context,
            token,
            props,
            object : CallbackListener<ConversationsClient> {
                override fun onSuccess(result: ConversationsClient?) {
                    conversationsClient = result
                    Logger.d("Waiting room initialization successful")
                }

                override fun onError(errorInfo: ErrorInfo?) {
                    super.onError(errorInfo)
                    Logger.e(errorInfo?.message ?: "", errorInfo?.reason)
                }
            })
    }

    private fun loadChannels() {
        if (conversationsClient != null && !conversationsClient?.myConversations.isNullOrEmpty()) {
            Logger.d("Conversations found")
        }
        return
    }

    private fun joinConversation(conversation: Conversation) {

        if (conversation.status == Conversation.ConversationStatus.JOINED) {
            Logger.d("Already joined")
        } else {
            conversation.join(object : StatusListener {
                override fun onSuccess() {

                }

                override fun onError(errorInfo: ErrorInfo) {
                    Logger.e(errorInfo?.message ?: "", errorInfo?.reason)
                }
            })
        }
    }
}