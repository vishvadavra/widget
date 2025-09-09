package com.epic.widgetwall.platform

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String?): T? {
    return try {
        enumValueOf<T>(name = name ?: return null)
    } catch (_: Exception) {
        null
    }
}
