package com.typ.sentinel.systems.shs.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.typ.sentinel.systems.shs.SecureHistoryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface SecureHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: SecureHistoryEntry)

    @Query("SELECT * FROM secure_history ORDER BY timestamp DESC")
    suspend fun getAllEntries(): List<SecureHistoryEntry>

    @Query("SELECT * FROM secure_history WHERE id = :entryId")
    suspend fun getEntryById(entryId: Long): SecureHistoryEntry?

    @Query("DELETE FROM secure_history WHERE id = :entryId")
    suspend fun deleteEntry(entryId: Long)

    @Query("DELETE FROM secure_history")
    suspend fun clearHistory()
}
