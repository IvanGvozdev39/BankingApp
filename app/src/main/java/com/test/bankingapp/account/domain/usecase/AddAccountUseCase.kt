package com.test.bankingapp.account.domain.usecase

import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.models.AccountEntity
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(private val accountDao: AccountDao) {
    suspend fun execute(account: AccountEntity) {
        val existingCount = accountDao.countByAccountNumber(account.accountNumber)
        if (existingCount == 0) {
            accountDao.insert(account)
        }
    }
}
