package com.test.bankingapp.room_db.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.bankingapp.util.Constants
import java.time.LocalDate

@Entity(tableName = Constants.TRANSACTION_TABLE_NAME)
data class TransactionEntity(
    val appliedIn: String,
    @PrimaryKey val number: Long,
    val date: LocalDate,
    val status: String,
    val amount: Int,
    val accountId: Long
)
