package com.test.bankingapp.room_db.domain.dao

import androidx.room.*
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transaction: TransactionEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)

    @Query("SELECT * FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE accountId = :accountNumber")
    fun getTransactionsForAccount(accountNumber: Long): Flow<List<TransactionEntity>> // Flow or LiveData

    @Query("SELECT * FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE accountId = :accountNumber ORDER BY date DESC LIMIT 5")
    fun getRecentTransactionsForAccount(accountNumber: Long): Flow<List<TransactionEntity>> // Flow or LiveData

}
