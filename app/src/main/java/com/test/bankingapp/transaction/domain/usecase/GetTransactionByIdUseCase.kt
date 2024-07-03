package com.test.bankingapp.transaction.domain.usecase

import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun execute(accountId: Long, transactionId: Long): Flow<TransactionEntity> {
        return transactionDao.getTransactionById(accountId, transactionId)
    }
}
