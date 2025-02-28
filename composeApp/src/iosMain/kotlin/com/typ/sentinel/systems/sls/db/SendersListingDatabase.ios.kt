package com.typ.sentinel.systems.sls.db

import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender

actual fun getDatabase(): SendersListingDatabase {
    return SendersListingDatabaseImpl()
}

class SendersListingDatabaseImpl : SendersListingDatabase {
    override suspend fun getSendersByType(type: ListType): List<ListedSender> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSender(sender: ListedSender) {
        TODO("Not yet implemented")
    }

    override suspend fun removeSender(sender: ListedSender) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBySender(sender: String) {
        TODO("Not yet implemented")
    }

}