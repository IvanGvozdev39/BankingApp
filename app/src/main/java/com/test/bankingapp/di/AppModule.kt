package com.test.bankingapp.di

import android.content.Context
import androidx.room.Room
import com.test.bankingapp.room_db.domain.AccountTransactionDatabase
import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AccountTransactionDatabase {
        return Room.databaseBuilder(
            context,
            AccountTransactionDatabase::class.java,
            "banking_app_db"
        ).build()
    }

    @Provides
    fun provideAccountDao(appDatabase: AccountTransactionDatabase): AccountDao {
        return appDatabase.accountDao()
    }

    @Provides
    fun provideTransactionDao(appDatabase: AccountTransactionDatabase): TransactionDao {
        return appDatabase.transactionDao()
    }
}
