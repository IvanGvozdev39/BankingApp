package com.test.bankingapp.transaction.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.transaction.domain.usecase.AddTransactionUseCase
import com.test.bankingapp.transaction.domain.usecase.GetAllTransactionsUseCase
import com.test.bankingapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTransactionsViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val context: Context
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<TransactionEntity>>(emptyList())
    val transactions: StateFlow<List<TransactionEntity>> get() = _transactions

    fun getAllTransactions() {
        viewModelScope.launch {
            val pref = context.getSharedPreferences(Constants.SHARED_PREF_S, Context.MODE_PRIVATE)
            val accountId = pref.getLong(Constants.PREF_SELECTED_ACCOUNT_ID_KEY, -1)
            if (accountId != -1L)
                getAllTransactionsUseCase.execute(accountId).collect { transactions ->
                    _transactions.value = transactions
                }
        }
    }
}
