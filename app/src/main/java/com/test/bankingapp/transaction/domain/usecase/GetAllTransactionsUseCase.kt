package com.test.bankingapp.transaction.domain.usecase

import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUseCase @Inject constructor(private val transactionDao: TransactionDao) {

    fun execute(accountNumber: Long): Flow<List<TransactionEntity>> {
        return transactionDao.getTransactionsForAccount(accountNumber)
    }
}
