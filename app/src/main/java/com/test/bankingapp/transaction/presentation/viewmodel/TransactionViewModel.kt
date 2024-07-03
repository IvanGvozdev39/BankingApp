package com.test.bankingapp.transaction.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.transaction.domain.usecase.AddTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {


    fun addTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            Log.d("eded32", "Attempting to add transaction: $transaction")
            addTransactionUseCase.execute(transaction)
            Log.d("eded32", "Transaction added successfully")
        }
    }
}
