package com.rznnike.configurablewidgetexample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ConfigurableWidgetConfigureActivity]
 */
class ConfigurableWidget : AppWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) { //do nothing
    }
}