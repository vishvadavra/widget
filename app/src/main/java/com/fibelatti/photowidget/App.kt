package com.epic.widgetwall

import android.app.Application
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composer
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.epic.widgetwall.platform.ConfigurationChangedReceiver
import com.epic.widgetwall.preferences.Appearance
import com.epic.widgetwall.preferences.UserPreferencesStorage
import com.epic.widgetwall.widget.DeleteStaleDataUseCase
import com.epic.widgetwall.widget.PhotoWidgetRescheduleWorker
import com.epic.widgetwall.widget.PhotoWidgetSyncWorker
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var userPreferencesStorage: UserPreferencesStorage

    @Inject
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var deleteStaleDataUseCase: DeleteStaleDataUseCase

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(hiltWorkerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        setupDebugMode()
        setupNightMode()
        setupDynamicColors()
        deleteStaleData()
        getReadyToWork()
    }

    @OptIn(ExperimentalComposeRuntimeApi::class)
    private fun setupDebugMode() {
        if (!BuildConfig.DEBUG) return

        Timber.plant(Timber.DebugTree())

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build(),
        )

        Composer.setDiagnosticStackTraceEnabled(enabled = true)
    }

    private fun setupNightMode() {
        val mode = when (userPreferencesStorage.appearance) {
            Appearance.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            Appearance.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun setupDynamicColors() {
        val dynamicColorsOptions = DynamicColorsOptions.Builder()
            .setThemeOverlay(R.style.AppTheme_Overlay)
            .setPrecondition { _, _ -> userPreferencesStorage.dynamicColors }
            .build()

        DynamicColors.applyToActivitiesIfAvailable(this, dynamicColorsOptions)
    }

    private fun deleteStaleData() {
        coroutineScope.launch {
            deleteStaleDataUseCase()
        }
    }

    private fun getReadyToWork() {
        ConfigurationChangedReceiver.register(context = this)

        PhotoWidgetRescheduleWorker.enqueueWork(context = this)
        PhotoWidgetSyncWorker.enqueueWork(context = this)
    }
}
