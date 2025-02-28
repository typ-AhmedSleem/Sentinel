package com.typ.sentinel.systems.shs.db

import com.typ.sentinel.systems.shs.SecureHistoryEntry
import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender
import com.typ.sentinel.systems.sls.db.ListedSenderDao
import kotlinx.coroutines.flow.Flow

internal actual fun getSecureHistoryDatabase(): SecureHistoryDao {
    return SecureHistoryDaoImpl()
}

private class SecureHistoryDaoImpl : SecureHistoryDao {
    override suspend fun insert(entry: SecureHistoryEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllEntries(): List<SecureHistoryEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun getEntryById(entryId: Long): SecureHistoryEntry? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEntry(entryId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun clearHistory() {
        TODO("Not yet implemented")
    }

}