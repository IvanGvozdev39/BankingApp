package com.test.bankingapp.room_db.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.bankingapp.util.Constants

@Entity(tableName = Constants.ACCOUNT_TABLE_NAME)
data class AccountEntity(
    val accountName: String,
    @PrimaryKey val accountNumber: Long,
    val cardNumber: Long
)