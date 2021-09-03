package com.rznnike.updatingwidgetexample

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

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
            val delay = 6000
            var time = System.currentTimeMillis()
            if (!force) {
                time += delay
            }
            alarm.set(AlarmManager.RTC_WAKEUP, time, getPendingIntent(context))
        }

        @SuppressLint("UnspecifiedImmutableFlag")
        private fun getPendingIntent(context: Context): PendingIntent? {
            val alarmIntent = Intent(context, AlarmReceiver::class.java)
            alarmIntent.action = BROADCAST_INTENT
            return if (Build.VERSION.SDK_INT >= 23) {
                PendingIntent.getBroadcast(
                    context,
                    0,
                    alarmIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                )
            } else {
                PendingIntent.getBroadcast(
                    context,
                    0,
                    alarmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            }
        }
    }
}