package com.typ.sentinel.systems.sls.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender

@Dao
interface ListedSenderDao {

    @Query("SELECT * FROM listed_senders")
    suspend fun getAllSenders(): List<ListedSender>

    @Query("SELECT * FROM listed_senders WHERE type = :type")
    suspend fun getSendersByType(type: ListType): List<ListedSender>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSender(sender: ListedSender)

    @Delete
    suspend fun removeSender(sender: ListedSender)

    @Query("DELETE FROM listed_senders WHERE sender = :sender")
    suspend fun deleteBySender(sender: String)
}
