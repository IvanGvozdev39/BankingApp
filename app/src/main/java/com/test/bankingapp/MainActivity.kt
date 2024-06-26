package com.test.bankingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.domain.model.Transaction
import com.test.bankingapp.account.presentation.screens.AccountItem
import com.test.bankingapp.account.presentation.screens.AccountScreen
import com.test.bankingapp.ui.theme.BankingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val accounts = listOf(
                        Account("Saving Account", "9121219291221", "**** 1234")
                    )
                    val transactions = listOf(
                        Transaction("OOO \"Company\"", "01.06.2024", "Executed", "$10.09"),
                        Transaction("OOO \"Company2\"", "02.06.2024", "Declined", "$10.09"),
                        Transaction("OOO \"Company\"", "06.06.2024", "In progress", "$10.09"),
                        Transaction("OOO \"Company\"", "01.06.2024", "Executed", "$10.09")
                    )
                    AccountScreen(accounts, transactions)
                }
            }
        }
    }
}