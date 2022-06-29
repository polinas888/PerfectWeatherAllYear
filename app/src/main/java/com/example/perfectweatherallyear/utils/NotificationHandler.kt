package com.example.perfectweatherallyear.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.perfectweatherallyear.MainActivity
import com.example.perfectweatherallyear.R

const val NOTIFICATION_ID = 1
const val CHANNEL_ID  = "reminder_channel_id"
class NotificationHandler {

    companion object {
        fun createNotification(application: Application, context: Context, contentMessage: String): Notification {
            createNotificationChannel(application)
            val intent = Intent(context, MainActivity:: class.java).apply{
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
            return notification
        }

        fun createNotificationChannel(application: Application) {
            application.apply {
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
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.createNotificationChannel(channel)
                }
            }
        }
    }
}