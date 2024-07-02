package com.test.bankingapp.room_db.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.util.Constants

@Database(
    entities = [AccountEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AccountTransactionDatabase: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        var INSTANCE: AccountTransactionDatabase? = null

        fun getDatabase(context: Context): AccountTransactionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AccountTransactionDatabase::class.java,
                    Constants.ACCOUNT_TRANSACTION_DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}