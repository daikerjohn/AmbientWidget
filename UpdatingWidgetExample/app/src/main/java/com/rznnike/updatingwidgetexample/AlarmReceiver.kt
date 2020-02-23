package com.rznnike.updatingwidgetexample

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            intent?.let {
                UpdateJobService.enqueueWork(context, intent)
            }
        }
    }

    companion object {
        private const val BROADCAST_INTENT = "com.rznnike.updatingwidgetexample.alarm"

        fun cancelAlarm(context: Context) {
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarm.cancel(getPendingIntent(context))
        }

        fun setAlarm(context: Context, force: Boolean) {
            cancelAlarm(context)
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val delay = 60000
            var time = System.currentTimeMillis()
            if (!force) {
                time += delay
            }
            alarm.set(AlarmManager.RTC_WAKEUP, time, getPendingIntent(context))
        }

        private fun getPendingIntent(context: Context): PendingIntent? {
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            alarmIntent.action = BROADCAST_INTENT
            return PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        }
    }
}