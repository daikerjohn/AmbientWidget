package com.rznnike.updatingwidgetexample

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.*
import org.json.JSONObject
import java.util.*
import java.util.concurrent.atomic.AtomicReference

//import io.netty.handler.codec.http.HttpResponse


class UpdateJobService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        /*
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val requestQueue = RequestQueue(cache, network).apply {
            start()
        }
        */

        // generates random number
        val random = Random(System.currentTimeMillis())
        val randomInt = random.nextInt(999)
        var lastUpdate = "R: $randomInt"
        // Reaches the view on widget and displays the number
        val view = RemoteViews(packageName, R.layout.updating_widget)

        val volleyQueue = Volley.newRequestQueue(this)
        volleyQueue.start()
        val url = "https://api.ambientweather.net/v1/devices?apiKey=35b92298d3d74e9286f1b53740ceee002299df52b3eb4c57a7e6c70de716a8ec&applicationKey=56818608e8b644d7bd29c3eb55d008656bb2376083014209925edbc39150e567"
        val jsonObjectRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                lastUpdate = "Response: %s".format(response.toString())
                val firstItem = response.get(0)
                var theStuff = (firstItem as JSONObject).getJSONObject("lastData")
                lastUpdate = theStuff.get("tempf").toString() //response.get(0).toString()
                    //.[0]["lastData"]["tempF"].toString()
                view.setTextViewText(R.id.tvWidget, lastUpdate)
                val theWidget = ComponentName(this, UpdatingWidget::class.java)
                val manager = AppWidgetManager.getInstance(this)
                manager.updateAppWidget(theWidget, view)
                // create new alarm
                AlarmReceiver.setAlarm(applicationContext, false)
                stopSelf()
            },
            { error ->
                lastUpdate = "ERROR: %s".format(error.toString())
                // TODO: Handle error
            }
        )

        // add the json request object created
        // above to the Volley request queue
        volleyQueue.add(jsonObjectRequest)

        //view.setTextViewText(R.id.tvWidget, lastUpdate)
        //val theWidget = ComponentName(this, UpdatingWidget::class.java)
        //val manager = AppWidgetManager.getInstance(this)
        //manager.updateAppWidget(theWidget, view)
        // create new alarm
        //AlarmReceiver.setAlarm(applicationContext, false)
        //stopSelf()
    }

    companion object {
        private const val JOB_ID = 4242

        fun enqueueWork(context: Context, intent: Intent) =
            enqueueWork(context, ComponentName(context, UpdateJobService::class.java), JOB_ID, intent)
    }
}