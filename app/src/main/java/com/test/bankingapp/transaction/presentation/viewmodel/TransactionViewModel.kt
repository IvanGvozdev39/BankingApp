package com.test.bankingapp.transaction.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.transaction.domain.usecase.AddTransactionUseCase
import com.test.bankingapp.transaction.domain.usecase.GetTransactionByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase
) : ViewModel() {

    private val _transaction = MutableStateFlow<TransactionEntity?>(null)
    val transaction: StateFlow<TransactionEntity?> get() = _transaction

    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            addTransactionUseCase.execute(transaction)
        }
    }

    fun getTransactionById(accountId: Long, transactionId: Long) {
        viewModelScope.launch {
            getTransactionByIdUseCase.execute(accountId, transactionId).collect { transaction ->
                _transaction.value = transaction
            }
        }
    }
}