package com.test.bankingapp.room_db.domain.dao

import androidx.room.*
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transaction: TransactionEntity)

    @Query("DELETE FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE accountId =:accountNumber")
    fun deleteAllTransactionsForAccount(accountNumber: Long)

    @Query("SELECT * FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE accountId = :accountNumber ORDER BY date DESC")
    fun getTransactionsForAccount(accountNumber: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE accountId = :accountNumber ORDER BY date DESC LIMIT 5")
    fun getRecentTransactionsForAccount(accountNumber: Long): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE accountId = :accountId " +
            "AND number = :transactionId LIMIT 1")
    fun getTransactionById(accountId: Long, transactionId: Long): Flow<TransactionEntity>

}
