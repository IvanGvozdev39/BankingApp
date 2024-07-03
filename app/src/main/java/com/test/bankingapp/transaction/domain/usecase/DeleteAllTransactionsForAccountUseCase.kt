package com.test.bankingapp.transaction.domain.usecase

import com.test.bankingapp.room_db.domain.dao.TransactionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAllTransactionsForAccountUseCase @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun execute(accountNumber: Long) {
        withContext(Dispatchers.IO) {
            transactionDao.deleteAllTransactionsForAccount(accountNumber)
        }
    }
}
