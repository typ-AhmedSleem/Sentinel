package com.typ.sentinel.systems.sls.db

import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender

internal actual fun getSendersListingDatabase(): ListedSenderDao {
    return ListedSenderDaoImpl()
}

class ListedSenderDaoImpl : ListedSenderDao {

    override suspend fun getAllSenders(): List<ListedSender> {
        TODO("Not yet implemented")
    }

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