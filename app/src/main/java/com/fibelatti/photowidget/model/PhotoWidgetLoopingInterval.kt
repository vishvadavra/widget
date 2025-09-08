package com.fibelatti.photowidget.model

import android.os.Parcelable
import java.util.concurrent.TimeUnit
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoWidgetLoopingInterval(
    val repeatInterval: Long,
    val timeUnit: TimeUnit,
) : Parcelable {

    companion object {

        const val MAX_DEFAULT: Long = 60
        const val MAX_HOURS: Long = 24
        const val MAX_DAYS: Long = 31
        const val MIN_DEFAULT: Long = 1
        const val MIN_SECONDS: Long = 5

        val ONE_DAY = PhotoWidgetLoopingInterval(
            repeatInterval = 24,
            timeUnit = TimeUnit.HOURS,
        )
    }
}

fun PhotoWidgetLoopingInterval.repeatIntervalAsSeconds(): Long = timeUnit.toSeconds(repeatInterval)

fun PhotoWidgetLoopingInterval.intervalRange(): ClosedFloatingPointRange<Float> {
    return when (timeUnit) {
        TimeUnit.SECONDS -> {
            PhotoWidgetLoopingInterval.MIN_SECONDS.toFloat()..PhotoWidgetLoopingInterval.MAX_DEFAULT.toFloat()
        }

        TimeUnit.HOURS -> {
            PhotoWidgetLoopingInterval.MIN_DEFAULT.toFloat()..PhotoWidgetLoopingInterval.MAX_HOURS.toFloat()
        }

        TimeUnit.DAYS -> {
            PhotoWidgetLoopingInterval.MIN_DEFAULT.toFloat()..PhotoWidgetLoopingInterval.MAX_DAYS.toFloat()
        }

        else -> {
            PhotoWidgetLoopingInterval.MIN_DEFAULT.toFloat()..PhotoWidgetLoopingInterval.MAX_DEFAULT.toFloat()
        }
    }
}

fun Long.minutesToLoopingInterval(): PhotoWidgetLoopingInterval {
    return if (this <= PhotoWidgetLoopingInterval.MAX_DEFAULT) {
        PhotoWidgetLoopingInterval(
            repeatInterval = TimeUnit.MINUTES.toMinutes(this),
            timeUnit = TimeUnit.MINUTES,
        )
    } else {
        PhotoWidgetLoopingInterval(
            repeatInterval = TimeUnit.MINUTES.toHours(this),
            timeUnit = TimeUnit.HOURS,
        )
    }
}

fun Long.secondsToLoopingInterval(): PhotoWidgetLoopingInterval {
    return when {
        this <= PhotoWidgetLoopingInterval.MAX_DEFAULT -> {
            PhotoWidgetLoopingInterval(
                repeatInterval = TimeUnit.SECONDS.toSeconds(this),
                timeUnit = TimeUnit.SECONDS,
            )
        }

        this <= TimeUnit.MINUTES.toSeconds(PhotoWidgetLoopingInterval.MAX_DEFAULT) -> {
            PhotoWidgetLoopingInterval(
                repeatInterval = TimeUnit.SECONDS.toMinutes(this),
                timeUnit = TimeUnit.MINUTES,
            )
        }

        this <= TimeUnit.HOURS.toSeconds(PhotoWidgetLoopingInterval.MAX_HOURS) -> {
            PhotoWidgetLoopingInterval(
                repeatInterval = TimeUnit.SECONDS.toHours(this),
                timeUnit = TimeUnit.HOURS,
            )
        }

        else -> {
            PhotoWidgetLoopingInterval(
                repeatInterval = TimeUnit.SECONDS.toDays(this),
                timeUnit = TimeUnit.DAYS,
            )
        }
    }
}

/**
 * This enum used to act as a set of pre-defined intervals for cycling photos in widgets.
 * This has been migrated for a more customizable approach where users can pick their intervals instead.
 * This class is kept to allow migrating the persisted data of any widgets that has been configured with it.
 */
@Suppress("Unused")
enum class LegacyPhotoWidgetLoopingInterval(
    val repeatInterval: Long,
    val timeUnit: TimeUnit,
) {

    ONE_DAY(
        repeatInterval = 24,
        timeUnit = TimeUnit.HOURS,
    ),
    TWELVE_HOURS(
        repeatInterval = 12,
        timeUnit = TimeUnit.HOURS,
    ),
    EIGHT_HOURS(
        repeatInterval = 8,
        timeUnit = TimeUnit.HOURS,
    ),
    SIX_HOURS(
        repeatInterval = 6,
        timeUnit = TimeUnit.HOURS,
    ),
    TWO_HOURS(
        repeatInterval = 2,
        timeUnit = TimeUnit.HOURS,
    ),
    ONE_HOUR(
        repeatInterval = 1,
        timeUnit = TimeUnit.HOURS,
    ),
    THIRTY_MINUTES(
        repeatInterval = 30,
        timeUnit = TimeUnit.MINUTES,
    ),
    FIFTEEN_MINUTES(
        repeatInterval = 15,
        timeUnit = TimeUnit.MINUTES,
    ),
}
