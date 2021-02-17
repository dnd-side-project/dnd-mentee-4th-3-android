package com.thisteampl.jackpot.main.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.thisteampl.jackpot.R
import kotlinx.coroutines.Job


/*
* FCM, 파이어베이스를 이용한 Notification 서비스
* https://firebase.google.com/docs/cloud-messaging/android/client?hl=ko#kotlin+ktx_1
* https://herojoon-dev.tistory.com/18
* */



class MyFirebaseMessagingService : FirebaseMessagingService(){
    private val TAG = "FCMService"

    /**
     * 메시지 수신받는 메소드
     * @param msg
     */
    override fun onMessageReceived(msg: RemoteMessage) {
        Log.i("### msg : ", msg.toString())
        if (msg.data.size > 0) {
            Log.i("### data : ", msg.data.toString())
            sendTopNotification(msg.data["title"], msg.data["content"])
            if (true) {
                scheduleJob()
            } else {
                handleNow()
            }
        }
        if (msg.notification != null) {
            Log.i("### notification : ", msg.notification!!.body!!)
            sendTopNotification(msg.notification!!.title, msg.notification!!.body)
        }
    }

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder()
            .setService(MyJobService::class.java)
            .setTag("my-job-tag")
            .build()
        dispatcher.schedule(myJob)
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    private fun sendTopNotification(
        title: String?,
        content: String?
    ) {
        val CHANNEL_DEFAULT_IMPORTANCE = "jackpot_notify"
        val ONGOING_NOTIFICATION_ID = 1
        val notificationIntent = Intent(this, MyFirebaseMessagingService::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification: Notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(R.drawable.common_full_open_on_phone)
                    .setContentIntent(pendingIntent)
                    .build()
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_DEFAULT_IMPORTANCE,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(ONGOING_NOTIFICATION_ID, notification)
    }
}