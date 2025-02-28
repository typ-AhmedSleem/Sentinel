package com.typ.sentinel.systems.shs

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.typ.sentinel.systems.shs.db.SecureHistoryDao
import com.typ.sentinel.systems.shs.db.getSecureHistoryDatabase
import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender
import com.typ.sentinel.systems.sls.db.ListedSenderDao
import com.typ.sentinel.systems.sls.db.getSendersListingDatabase
import kotlinx.datetime.Clock

class SecureHistoryManager(private val dao: SecureHistoryDao = getSecureHistoryDatabase()) {

    suspend fun insert(entry: SecureHistoryEntry) = dao.insert(entry)

    suspend fun getAllEntries() = dao.getAllEntries()

    suspend fun getEntryById(entryId: Long) = dao.getEntryById(entryId)

    suspend fun deleteEntry(entryId: Long) = dao.deleteEntry(entryId)

    suspend fun clearHistory() = dao.clearHistory()


}