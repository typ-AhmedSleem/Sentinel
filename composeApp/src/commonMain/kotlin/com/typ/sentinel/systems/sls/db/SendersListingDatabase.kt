package com.typ.sentinel.systems.sls.db

import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender
import kotlin.concurrent.Volatile

interface SendersListingDatabase {
    suspend fun getSendersByType(type: ListType): List<ListedSender>
    suspend fun insertSender(sender: ListedSender)
    suspend fun removeSender(sender: ListedSender)
    suspend fun deleteBySender(sender: String)
}

expect fun getDatabase(): SendersListingDatabase