package com.ari.prodvizhenie.calendar.data.local

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ari.prodvizhenie.MainActivity
import com.ari.prodvizhenie.R

class CreateNotification(
    private var context: Context,
    private var title: String,
    private var msg: String
) {

    private val channelId = "My_channel"
    private val channelName = "My channel name"
    private val notificationManager =
        context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: NotificationCompat.Builder

    fun showNotification() {
        notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setSmallIcon(R.drawable.ic_notification)

        notificationBuilder.setContentIntent(pendingIntent)

        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(msg)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE)
        notificationManager.notify(100, notificationBuilder.build())
    }
}