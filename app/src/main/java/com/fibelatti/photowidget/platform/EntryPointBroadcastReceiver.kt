package com.epic.widgetwall.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.epic.widgetwall.di.PhotoWidgetEntryPoint
import com.epic.widgetwall.di.entryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class EntryPointBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("Broadcast received (action=${intent.action})")

        Handler(Looper.getMainLooper()).post {
            val entryPoint: PhotoWidgetEntryPoint = entryPoint(context)
            entryPoint.coroutineScope().launch {
                doWork(context = context, intent = intent, entryPoint = entryPoint)
            }
        }
    }

    abstract suspend fun doWork(context: Context, intent: Intent, entryPoint: PhotoWidgetEntryPoint)
}
