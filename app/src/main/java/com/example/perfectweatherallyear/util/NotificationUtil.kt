package com.example.perfectweatherallyear.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.perfectweatherallyear.R

const val CHANNEL_ID = "forecast notification"
const val NOTIFICATION_ID = 1

class NotificationUtil {

    companion object {
        @SuppressLint("LaunchActivityFromNotification")
        fun displayNotification(context: Context) {

            val buttonIntent = Intent(context, NotificationButtonReceiver::class.java)
            buttonIntent.putExtra("notificationId",NOTIFICATION_ID)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, buttonIntent, 0)

            createNotificationChannel(context)
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.weather)
                .setContentTitle("new forecast")
                .setContentText("updated forecast information")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.close_black, "CLOSE", pendingIntent)
                .setAutoCancel(true)

            val notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build())
        }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName: CharSequence = "new forecast notification"
                val channelDescription = "updated forecast notification"
                val channelImportance: Int = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                    description = channelDescription
                }

                val notificationManager: NotificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}