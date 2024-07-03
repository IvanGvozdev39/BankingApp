package com.test.bankingapp.account.domain.usecase

import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetRecentTransactionsUseCase @Inject constructor(private val transactionDao: TransactionDao) {

    fun execute(accountNumber: Long): Flow<List<TransactionEntity>> {
        return transactionDao.getRecentTransactionsForAccount(accountNumber)
    }
}
