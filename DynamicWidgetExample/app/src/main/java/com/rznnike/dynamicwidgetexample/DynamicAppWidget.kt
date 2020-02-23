package com.rznnike.dynamicwidgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class DynamicAppWidget : AppWidgetProvider() {
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
    ) {
        // See the dimensions and
        val options = appWidgetManager.getAppWidgetOptions(appWidgetId)
        // Get min width and height.
        val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        val minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        // Construct the RemoteViews object
        val views = getRemoteViews(context, minWidth, minHeight)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onAppWidgetOptionsChanged(
            context: Context?,
            appWidgetManager: AppWidgetManager?,
            appWidgetId: Int,
            newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        // See the dimensions and
        val options = appWidgetManager!!.getAppWidgetOptions(appWidgetId)
        // Get min width and height.
        val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        val minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        // Construct the RemoteViews object
        val views = getRemoteViews(context!!, minWidth, minHeight)
        appWidgetManager.updateAppWidget(appWidgetId, views)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    /**
     * Determine appropriate view based on row or column provided.
     */
    private fun getRemoteViews(context: Context, minWidth: Int, minHeight: Int): RemoteViews {
        // First find out rows and columns based on width provided.
        val rows = getCellsForSize(minHeight)
        val columns = getCellsForSize(minWidth)
        val views = when (columns) {
            1 -> RemoteViews(context.packageName, R.layout.dynamic_app_widget_1x1)
            2 -> RemoteViews(context.packageName, R.layout.dynamic_app_widget_2x1)
            3 -> RemoteViews(context.packageName, R.layout.dynamic_app_widget_3x1)
            else -> RemoteViews(context.packageName, R.layout.dynamic_app_widget_4x1)
        }
        // Construct an Intent object includes web address.
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"))
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        // Here the basic operations the remote view can do.
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent)

        return views
    }

    /**
     * Returns number of cells needed for given size of the widget.
     *
     * @param size Widget size in dp.
     * @return Size in number of cells.
     */
    private fun getCellsForSize(size: Int): Int {
        val n = (size + 30) / 70
        return if (n > 0) n else 1
    }
}