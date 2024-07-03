package com.test.bankingapp.account.domain.usecase

import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.models.AccountEntity
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAccountsUseCase @Inject constructor(private val accountDao: AccountDao) {

    fun execute(): Flow<List<AccountEntity>> {
        return accountDao.getAllAccounts()
    }
}
