package com.test.bankingapp.room_db.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.test.bankingapp.room_db.data.constants.AccountFieldNames
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(account: AccountEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity)

    @Query("SELECT * FROM ${Constants.ACCOUNT_TABLE_NAME}")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM ${Constants.ACCOUNT_TABLE_NAME} WHERE ${AccountFieldNames.ACCOUNT_NUMBER} = :accountNumber")
    fun getAccount(accountNumber: Long): Flow<AccountEntity?>

    @Query("SELECT COUNT(*) FROM ${Constants.ACCOUNT_TABLE_NAME} WHERE ${AccountFieldNames.ACCOUNT_NUMBER} = :accountNumber")
    suspend fun countByAccountNumber(accountNumber: Long): Int
}
