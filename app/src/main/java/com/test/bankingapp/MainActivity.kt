package com.test.bankingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.domain.model.Transaction
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
                        Account("Saving Account", "9121219291221", "**** 1234"),
                        Account("My first account", "9121219291221", "**** 1234"),
                        Account("For travelling", "9121219291221", "**** 1234")
                    )
                    val transactions = listOf(
                        Transaction("OOO 'Company'", "26.06.2024", stringResource(id = R.string.executed),"$10.09"),
                        Transaction("OOO 'Enterprise'", "24.06.20242", stringResource(id = R.string.declined),"$11"),
                        Transaction("OAO 'Syndicate'", "24.06.20242", stringResource(id = R.string.declined),"$25.99")
                    )
                    AccountScreen(accounts = accounts, transactions = transactions)
                }
            }
        }
    }
}