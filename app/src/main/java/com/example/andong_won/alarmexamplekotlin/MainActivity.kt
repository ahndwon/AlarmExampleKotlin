package com.example.andong_won.alarmexamplekotlin

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var pendingIntent: PendingIntent? = null

    companion object {
        const val ALARM_REQUEST_CODE = 133
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val alarmIntent = Intent(this, AlarmReceiver::class.java)
        pendingIntent =
                PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, alarmIntent, 0)

        start_alarm_button.setOnClickListener {
            val getInterval = input_interval_time.text.toString().trim()

            if (!getInterval.equals("") && !getInterval.equals("0"))
                triggerAlarmManager(getTimeInterval(getInterval))
        }

        stop_alarm_button.setOnClickListener { stopAlarmManager() }
    }

    private fun getTimeInterval(getInterval: String): Int {
        val interval = Integer.parseInt(getInterval)

        return when {
            seconds_radio_button.isChecked -> interval
            minutes_radio_button.isChecked -> interval * 60
            hours_radio_button.isChecked -> interval * 60 * 60
            else -> {
                0
            }
        }
    }

    private fun triggerAlarmManager(alarmTriggerTime: Int) {
        val calendar = Calendar.getInstance()

        calendar.add(Calendar.SECOND, alarmTriggerTime)

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        manager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(this,
                "Alarm Set for $alarmTriggerTime seconds.",
                Toast.LENGTH_SHORT).show()
    }

    private fun stopAlarmManager() {
        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.cancel(pendingIntent)

        stopService(Intent(this, AlarmSoundService::class.java))

        val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID)

        Toast.makeText(this, "Alarm Canceled/Stop by User", Toast.LENGTH_SHORT).show()
    }
}
