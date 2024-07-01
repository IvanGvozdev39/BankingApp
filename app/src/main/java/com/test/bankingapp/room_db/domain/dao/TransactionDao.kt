package com.test.bankingapp.room_db.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.test.bankingapp.room_db.data.constants.TransactionFieldNames
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

    @Query("SELECT * FROM ${Constants.TRANSACTION_TABLE_NAME} WHERE ${TransactionFieldNames.ACCOUNT_ID} = :accountNumber")
    fun getTransactionsForAccount(accountNumber: Long): Flow<List<TransactionEntity>>


}