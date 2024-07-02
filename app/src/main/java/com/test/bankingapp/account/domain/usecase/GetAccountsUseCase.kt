package com.test.bankingapp.account.domain.usecase

import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.models.AccountEntity

class GetAccountsUseCase(private val accountDao: AccountDao) {

    suspend fun execute(): List<AccountEntity> {
        var accountList: List<AccountEntity> = emptyList()
        accountDao.getAllAccounts().collect { accounts ->
            accountList = accounts
        }
        return accountList
    }
}
