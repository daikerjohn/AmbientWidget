package com.rznnike.simplewidgetexample

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class SimpleAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) { // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) { // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.simple_app_widget)
        // Construct an Intent object includes web address.
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"))
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        @SuppressLint("UnspecifiedImmutableFlag")
        val pendingIntent = if (Build.VERSION.SDK_INT >= 23) {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                0
            )
        }
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}