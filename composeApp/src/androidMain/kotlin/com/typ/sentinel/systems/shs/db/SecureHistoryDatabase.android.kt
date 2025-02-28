package com.typ.sentinel.systems.shs.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.typ.sentinel.support.appContext
import com.typ.sentinel.systems.shs.SecureHistoryEntry
import com.typ.sentinel.systems.sls.ListedSender
import com.typ.sentinel.systems.sls.db.ListedSenderDao
import com.typ.sentinel.systems.sls.db.SendersListingDatabase

internal actual fun getSecureHistoryDatabase(): SecureHistoryDao {
    return SecureHistoryDatabase.getInstance(appContext!!).secureHistoryDao()
}


@Database(
    entities = [SecureHistoryEntry::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SecureHistoryTypeConverters::class)
abstract class SecureHistoryDatabase : RoomDatabase() {

    abstract fun secureHistoryDao(): SecureHistoryDao

    companion object {

        @Volatile
        private var INSTANCE: SecureHistoryDatabase? = null

        fun getInstance(context: Context): SecureHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SecureHistoryDatabase::class.java,
                    "secure_history_database"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}