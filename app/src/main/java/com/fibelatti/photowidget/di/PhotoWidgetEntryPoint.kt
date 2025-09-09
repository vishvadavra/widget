package com.epic.widgetwall.di

import com.epic.widgetwall.configure.PhotoWidgetPinningCache
import com.epic.widgetwall.configure.SavePhotoWidgetUseCase
import com.epic.widgetwall.platform.PhotoDecoder
import com.epic.widgetwall.preferences.UserPreferencesStorage
import com.epic.widgetwall.widget.CyclePhotoUseCase
import com.epic.widgetwall.widget.LoadPhotoWidgetUseCase
import com.epic.widgetwall.widget.PhotoWidgetAlarmManager
import com.epic.widgetwall.widget.PrepareCurrentPhotoUseCase
import com.epic.widgetwall.widget.data.PhotoWidgetStorage
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PhotoWidgetEntryPoint {

    fun userPreferencesStorage(): UserPreferencesStorage

    fun photoWidgetStorage(): PhotoWidgetStorage

    fun photoWidgetPinningCache(): PhotoWidgetPinningCache

    fun photoWidgetAlarmManager(): PhotoWidgetAlarmManager

    fun loadPhotoWidgetUseCase(): LoadPhotoWidgetUseCase

    fun savePhotoWidgetUseCase(): SavePhotoWidgetUseCase

    fun prepareCurrentPhotoUseCase(): PrepareCurrentPhotoUseCase

    fun cyclePhotoUseCase(): CyclePhotoUseCase

    fun photoDecoder(): PhotoDecoder

    fun coroutineScope(): CoroutineScope
}
