package com.test.bankingapp.account.domain.usecase

import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.transaction.domain.usecase.DeleteAllTransactionsForAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val accountDao: AccountDao,
    private val deleteAllTransactionsForAccountUseCase: DeleteAllTransactionsForAccountUseCase
) {
    suspend fun execute(account: AccountEntity) {
        withContext(Dispatchers.IO) {
            val existingCount = accountDao.countByAccountNumber(account.accountNumber)
            if (existingCount != 0) {
                deleteAllTransactionsForAccountUseCase.execute(account.accountNumber)
                accountDao.delete(account)
            }
        }
    }
}
