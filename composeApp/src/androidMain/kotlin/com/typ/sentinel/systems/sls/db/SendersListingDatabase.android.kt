package com.typ.sentinel.systems.sls.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.typ.sentinel.support.appContext
import com.typ.sentinel.systems.sls.ListType
import com.typ.sentinel.systems.sls.ListedSender

internal actual fun getSendersListingDatabase(): ListedSenderDao {
    return SendersListingDatabase.getInstance(appContext!!).listedSenderDao()
}

@Database(
    entities = [ListedSender::class],
    version = 1,
    exportSchema = false
)
abstract class SendersListingDatabase : RoomDatabase() {

    abstract fun listedSenderDao(): ListedSenderDao

    companion object {

        @Volatile
        private var INSTANCE: SendersListingDatabase? = null

        fun getInstance(context: Context): SendersListingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SendersListingDatabase::class.java,
                    "senders_listing_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

