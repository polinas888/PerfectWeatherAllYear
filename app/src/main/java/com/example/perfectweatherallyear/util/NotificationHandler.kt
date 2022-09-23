package com.example.perfectweatherallyear.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.perfectweatherallyear.MainActivity
import com.example.perfectweatherallyear.R
import com.example.perfectweatherallyear.channelId
import com.example.perfectweatherallyear.channelName

class NotificationHandler {

    companion object {
        private val CHANNEL_ID = "reminder_channel_id"
        private val NOTIFICATION_ID = 1

        fun postNotification(context: Context, contentMessage: String) {
            if (CHANNEL_ID.isEmpty()) {
                createNotificationChannel(context)
            }
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.weather)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(contentMessage)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        }

        private fun createNotificationChannel(context: Context) {
            if (CHANNEL_ID.isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel =
                        NotificationChannel(
                            CHANNEL_ID,
                            "Channel_Default",
                            NotificationManager.IMPORTANCE_HIGH
                        )
                            .apply {
                                description = "This is default channel"
                            }
                    val notificationManager: NotificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }

         fun generateFirebaseNotification(context: Context, title: String, message: String) {
             val intent = Intent(context, MainActivity::class.java).apply {
                 flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
             }

             val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

             val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.close_black)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setContentIntent(pendingIntent)

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)
            }
            notificationManager.notify(0, builder.build())
        }
    }
}
