package com.rznnike.updatingwidgetexample

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import java.util.*

class UpdateService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
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
        return super.onStartCommand(intent, flags, startId)
    }
}