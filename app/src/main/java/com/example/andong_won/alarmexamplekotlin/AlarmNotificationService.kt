package com.example.andong_won.alarmexamplekotlin

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat


class AlarmNotificationService : IntentService(AlarmNotificationService::class.java.name) {
    private var alarmNotificationManager : NotificationManager? = null

    private val channelId = "alarm"

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onHandleIntent(intent: Intent?) {
        sendNotification("Wake Up! Wake Up! Alarm started!!")
    }

    private fun sendNotification(message: String) {
        val contentIntent = PendingIntent
                .getActivity(this, 0,
                        Intent(this, MainActivity::class.java),
                        PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmNotificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                .setContentText(message).setAutoCancel(true)

        alarmNotificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        alarmNotificationManager?.notify(NOTIFICATION_ID, alarmNotificationBuilder.build())
    }

}