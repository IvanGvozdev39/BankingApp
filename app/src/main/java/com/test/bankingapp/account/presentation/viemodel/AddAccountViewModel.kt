package com.test.bankingapp.account.presentation.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.bankingapp.account.domain.usecase.AddAccountUseCase
import com.test.bankingapp.room_db.domain.dao.AccountDao
import com.test.bankingapp.room_db.domain.dao.TransactionDao
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase
) : ViewModel() {

    fun addAccount(account: AccountEntity) {
        viewModelScope.launch {
            addAccountUseCase.execute(account)
        }
    }
}