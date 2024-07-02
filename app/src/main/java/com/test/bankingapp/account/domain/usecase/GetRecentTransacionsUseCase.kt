package com.test.bankingapp.account.domain.usecase

import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.TransactionEntity

class GetRecentTransactionsUseCase(private val transactionDao: TransactionDao) {

    suspend fun execute(accountNumber: Long, limit: Int = 5): List<TransactionEntity> {
        var transactionList: List<TransactionEntity> = emptyList()
        transactionDao.getRecentTransactionsForAccount(accountNumber).collect { transactions ->
            transactionList = transactions
        }
        return transactionList
    }
}
