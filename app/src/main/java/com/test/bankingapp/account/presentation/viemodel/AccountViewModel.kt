package com.test.bankingapp.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val transactionDao: TransactionDao
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<AccountEntity>>(emptyList())
    val accounts: StateFlow<List<AccountEntity>> get() = _accounts

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> get() = _transactions

    private val _recentTransactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val recentTransactions: StateFlow<List<TransactionEntity>> get() = _recentTransactions

    init {
        fetchAccounts()
    }

    private fun fetchAccounts() {
        viewModelScope.launch {
            accountDao.getAllAccounts().collect {
                _accounts.value = it
            }
        }
    }

    fun fetchTransactionsForAccount(accountNumber: Long) {
        viewModelScope.launch {
            transactionDao.getTransactionsForAccount(accountNumber).collect {
                _transactions.value = it
            }
        }
    }

    fun fetchRecentTransactionsForAccount(accountNumber: Long) {
        viewModelScope.launch {
            transactionDao.getRecentTransactionsForAccount(accountNumber).collect {
                _recentTransactions.value = it
            }
        }
    }

    fun addAccount(account: AccountEntity) {
        viewModelScope.launch {
            accountDao.insert(account)
        }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            accountDao.delete(account)
        }
    }
}
