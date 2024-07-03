package com.test.bankingapp.account.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.bankingapp.account.domain.usecase.AddAccountUseCase
import com.test.bankingapp.account.domain.usecase.GetAccountsUseCase
import com.test.bankingapp.transaction.domain.usecase.GetRecentTransactionsUseCase
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getRecentTransactionsUseCase: GetRecentTransactionsUseCase,
    private val context: Context
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<AccountEntity>>(emptyList())
    val accounts: StateFlow<List<AccountEntity>> get() = _accounts

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> get() = _transactions

    private val _recentTransactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val recentTransactions: StateFlow<List<TransactionEntity>> get() = _recentTransactions

    private val _selectedAccount = MutableStateFlow<AccountEntity?>(null)
    val selectedAccount: StateFlow<AccountEntity?> get() = _selectedAccount

    init {
        viewModelScope.launch {
            getAccountsUseCase.execute().collect { accounts ->
                _accounts.value = accounts
                if (_accounts.value.isNotEmpty() && _selectedAccount.value == null) {
                    val pref = context.getSharedPreferences(Constants.SHARED_PREF_S, Context.MODE_PRIVATE)
                    val selectedId = pref.getLong(Constants.PREF_SELECTED_ACCOUNT_ID_KEY, -1)
                    if (selectedId != -1L && _accounts.value.any { it.accountNumber == selectedId }) {
                        _selectedAccount.value = _accounts.value.first { it.accountNumber == selectedId }
                    } else
                        _selectedAccount.value = _accounts.value.first()
                }
            }
        }
        selectedAccount.value?.let {
            recentTransactionsForAccount(it.accountNumber)
        }
    }

    fun fetchTransactionsForAccount(accountNumber: Long) {
        viewModelScope.launch {
            getRecentTransactionsUseCase.execute(accountNumber).collect { transactions ->
                _transactions.value = transactions
            }
        }
    }

    fun recentTransactionsForAccount(accountNumber: Long) {
        viewModelScope.launch {
            getRecentTransactionsUseCase.execute(accountNumber).collect { transactions ->
                _recentTransactions.value = transactions
                Log.d("abcd123", "AccountViewModel recentTransactionsForAccount: $recentTransactions")
            }
        }
    }

    fun addAccount(account: AccountEntity) {
        viewModelScope.launch {
            addAccountUseCase.execute(account)
            _accounts.value = getAccountsUseCase.execute().first()
        }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            // Assume you have a DeleteAccountUseCase
//            deleteAccountUseCase.execute(account)
            _accounts.value = getAccountsUseCase.execute().first()
        }
    }

    fun selectAccount(account: AccountEntity) {
        _selectedAccount.value = account
    }
}
