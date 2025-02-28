package com.typ.sentinel.systems.sls

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listed_senders")
data class ListedSender(
    @PrimaryKey val sender: String, // Unique identifier (e.g., phone number, email, name)
    @ColumnInfo(name = "type") val type: ListType, // BLACKLIST or WHITELIST
    @ColumnInfo(name = "added_at") val addedAt: Long // Store as timestamp
)

enum class ListType {
    BLACKLIST,
    WHITELIST
}
