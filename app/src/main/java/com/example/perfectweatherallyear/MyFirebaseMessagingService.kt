package com.example.perfectweatherallyear

import com.example.perfectweatherallyear.util.NotificationHandler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.perfectweatherallyear"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            message.notification!!.title?.let { title ->
                message.notification!!.body?.let { message ->
                    NotificationHandler.generateFirebaseNotification(this, title, message
                    )
                }
            }
        }
    }
}