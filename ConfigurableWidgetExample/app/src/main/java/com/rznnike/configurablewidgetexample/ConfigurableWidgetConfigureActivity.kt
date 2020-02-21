package com.rznnike.configurablewidgetexample

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RemoteViews

/**
 * The configuration screen for the [ConfigurableWidget] AppWidget.
 */
class ConfigurableWidgetConfigureActivity : Activity() {
    private var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private var editTextIUrl: EditText? = null
    private var buttonAdd: Button? = null
    private var widgetManager: AppWidgetManager? = null
    private var views: RemoteViews? = null

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setResult(RESULT_CANCELED)
        // activity stuffs
        setContentView(R.layout.activity_widget_configure)
        editTextIUrl = findViewById(R.id.editTextIUrl)
        editTextIUrl?.setText("https://www.google.com/")
        // These steps are seen in the previous examples
        widgetManager = AppWidgetManager.getInstance(this)
        views = RemoteViews(this.packageName, R.layout.configurable_widget)
        // Find the widget id from the intent.
        intent.extras?.let {
            mAppWidgetId = it.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
        buttonAdd = findViewById<View>(R.id.buttonAdd) as Button
        buttonAdd?.setOnClickListener {
            // Gets user input
            val url = editTextIUrl?.text.toString()
            val actionIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val pending = PendingIntent.getActivity(this@ConfigurableWidgetConfigureActivity, 0, actionIntent, 0)
            views?.setOnClickPendingIntent(R.id.tvWidget, pending)
            views?.setTextViewText(R.id.tvWidget, url)
            widgetManager?.updateAppWidget(mAppWidgetId, views)
            val resultValue = Intent()
            // Set the results as expected from a 'configure activity'.
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
            setResult(RESULT_OK, resultValue)
            finish()
        }
    }
}