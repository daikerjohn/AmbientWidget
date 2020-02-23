package com.rznnike.updatingwidgetexample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

/**
 * Implementation of App Widget functionality.
 */
class UpdatingWidget : AppWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        AlarmReceiver.setAlarm(context, true)
        Log.d("UpdatingWidget: ", "onUpdate")
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("UpdatingWidget: ", "onReceive")
    }

    override fun onAppWidgetOptionsChanged(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            newOptions: Bundle
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d("UpdatingWidget: ", "onAppWidgetOptionsChanged")
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.d("UpdatingWidget: ", "onDeleted")
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("UpdatingWidget: ", "onEnabled")
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d("UpdatingWidget: ", "onDisabled")
    }

    override fun onRestored(context: Context, oldWidgetIds: IntArray, newWidgetIds: IntArray) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        Log.d("UpdatingWidget: ", "onRestored")
    }
}