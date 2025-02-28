package com.typ.sentinel.systems.shs.db

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

object SecureHistoryTypeConverters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return value?.let { Json.encodeToString(it) } ?: "[]"
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
