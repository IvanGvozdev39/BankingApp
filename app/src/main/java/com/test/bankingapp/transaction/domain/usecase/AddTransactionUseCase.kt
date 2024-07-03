package com.test.bankingapp.transaction.domain.usecase

import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun execute(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }
}
