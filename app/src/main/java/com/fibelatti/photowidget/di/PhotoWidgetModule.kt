package com.epic.widgetwall.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.memoryCacheMaxSizePercentWhileInBackground
import coil3.request.addLastModifiedToFileCacheKey
import coil3.request.allowHardware
import com.epic.widgetwall.widget.data.DisplayedPhotoDao
import com.epic.widgetwall.widget.data.ExcludedWidgetPhotoDao
import com.epic.widgetwall.widget.data.LocalPhotoDao
import com.epic.widgetwall.widget.data.PendingDeletionWidgetPhotoDao
import com.epic.widgetwall.widget.data.PhotoWidgetDatabase
import com.epic.widgetwall.widget.data.PhotoWidgetOrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

@Module
@InstallIn(SingletonComponent::class)
object PhotoWidgetModule {

    @Provides
    fun coroutineScope(): CoroutineScope = CoroutineScope(context = Dispatchers.Default + SupervisorJob())

    @Provides
    @Singleton
    fun photoWidgetDatabase(
        application: Application,
    ): PhotoWidgetDatabase = Room.databaseBuilder(
        context = application,
        klass = PhotoWidgetDatabase::class.java,
        name = "com.epic.widgetwall.db",
    ).build()

    @Provides
    fun localPhotoDao(
        photoWidgetDatabase: PhotoWidgetDatabase,
    ): LocalPhotoDao = photoWidgetDatabase.localPhotoDao()

    @Provides
    fun displayedPhotoDao(
        photoWidgetDatabase: PhotoWidgetDatabase,
    ): DisplayedPhotoDao = photoWidgetDatabase.displayedPhotoDao()

    @Provides
    fun photoWidgetOrderDao(
        photoWidgetDatabase: PhotoWidgetDatabase,
    ): PhotoWidgetOrderDao = photoWidgetDatabase.photoWidgetOrderDao()

    @Provides
    fun photoPendingDeletionPhotoDao(
        photoWidgetDatabase: PhotoWidgetDatabase,
    ): PendingDeletionWidgetPhotoDao = photoWidgetDatabase.pendingDeletionWidgetPhotoDao()

    @Provides
    fun excludedPhotoDao(
        photoWidgetDatabase: PhotoWidgetDatabase,
    ): ExcludedWidgetPhotoDao = photoWidgetDatabase.excludedWidgetPhotoDao()

    @Provides
    @Singleton
    @OptIn(ExperimentalCoilApi::class)
    fun imageLoader(@ApplicationContext context: Context): ImageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context = context, percent = 0.25)
                .build()
        }
        .memoryCacheMaxSizePercentWhileInBackground(percent = 0.25)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve(relative = "image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .interceptorCoroutineContext(Dispatchers.IO)
        .addLastModifiedToFileCacheKey(enable = true)
        .allowHardware(enable = false)
        .build()

    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun json(): Json = Json {
        encodeDefaults = true
        explicitNulls = false
        namingStrategy = JsonNamingStrategy.SnakeCase
    }
}
