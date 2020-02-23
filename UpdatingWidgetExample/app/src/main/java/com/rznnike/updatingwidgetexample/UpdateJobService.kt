package com.rznnike.updatingwidgetexample

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import java.util.*

class UpdateJobService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        // generates random number
        val random = Random()
        val randomInt = random.nextInt(100)
        val lastUpdate = "R: $randomInt"
        // Reaches the view on widget and displays the number
        val view = RemoteViews(packageName, R.layout.updating_widget)
        view.setTextViewText(R.id.tvWidget, lastUpdate)
        val theWidget = ComponentName(this, UpdatingWidget::class.java)
        val manager = AppWidgetManager.getInstance(this)
        manager.updateAppWidget(theWidget, view)
        // create new alarm
        AlarmReceiver.setAlarm(applicationContext, false)
        stopSelf()
    }

    companion object {
        private const val JOB_ID = 4242

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, ComponentName(context, UpdateJobService::class.java), JOB_ID, intent)
        }
    }
}