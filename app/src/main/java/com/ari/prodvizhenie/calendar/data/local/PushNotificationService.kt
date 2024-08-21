package com.ari.prodvizhenie.calendar.data.local

import android.app.Application
import android.content.Context
import android.util.Log
import com.ari.prodvizhenie.auth.domain.manager.LocalUserManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var localUserManager: LocalUserManager

    @Inject
    lateinit var appContext: Application

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("TAG", "onNewToken: $token")
        CoroutineScope(Dispatchers.Main).launch {
            localUserManager.saveDeviceToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        showNotification(appContext.applicationContext, message = message)
    }
}

fun showNotification(context: Context, message: RemoteMessage) {

    val notificationInfo = message.notification
    if (notificationInfo != null) {
        val title = notificationInfo.title
        val body = notificationInfo.body
        if (title != null && body != null) {
            val notification = CreateNotification(
                context = context,
                title = title,
                msg = body
            )
            notification.showNotification()
        }
    }
}