package com.example.andong_won.alarmexamplekotlin

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver.startWakefulService
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"ALARM!!!", Toast.LENGTH_SHORT).show()

        context?.startService(Intent(context, AlarmSoundService::class.java))

        val comp = ComponentName(context?.packageName, AlarmNotificationService::class.java.name)
        startWakefulService(context, intent?.setComponent(comp))
    }

}