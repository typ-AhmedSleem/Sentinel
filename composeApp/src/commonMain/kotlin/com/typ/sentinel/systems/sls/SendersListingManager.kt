package com.typ.sentinel.systems.sls

import com.typ.sentinel.systems.sls.db.ListedSenderDao
import com.typ.sentinel.systems.sls.db.getSendersListingDatabase
import kotlinx.datetime.Clock

class SendersListingManager(private val dao: ListedSenderDao = getSendersListingDatabase()) {

    suspend fun getWhitelisted(): List<ListedSender> = dao.getSendersByType(ListType.WHITELIST)

    suspend fun getBlacklisted(): List<ListedSender> = dao.getSendersByType(ListType.BLACKLIST)

    suspend fun blockSender(sender: String) {
        addSender(sender, ListType.BLACKLIST)
    }

    suspend fun unblockSender(sender: String) {
        removeSender(sender)
    }

    suspend fun whitelistSender(sender: String) {
        addSender(sender, ListType.WHITELIST)
    }

    suspend fun unWhitelistSender(sender: String) {
        removeSender(sender)
    }

    suspend fun addSender(sender: String, type: ListType) {
        dao.insertSender(
            ListedSender(
                type = type,
                sender = sender,
                addedAt = Clock.System.now().toEpochMilliseconds()
            )
        )
    }

    suspend fun removeSender(sender: String) {
        dao.deleteBySender(sender)
    }
}