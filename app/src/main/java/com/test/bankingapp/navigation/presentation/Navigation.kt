package com.test.bankingapp.navigation.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.domain.model.Transaction
import com.test.bankingapp.account.presentation.screens.AccountScreen
import com.test.bankingapp.transaction.presentation.screens.AllTransactionsScreen
import com.test.bankingapp.transaction.presentation.screens.TransactionScreen

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.AccountScreen.route) {
        composable(route = Screen.AccountScreen.route) {
            val accounts = listOf(
                Account("Saving Account", "9121219291221", "**** 1234"),
                Account("My first account", "9121219291221", "**** 1234"),
                Account("For travelling", "9121219291221", "**** 1234")
            )
            val transactions = listOf(
                Transaction("OOO 'Company'", "26.06.2024", stringResource(id = R.string.in_progress),"$10.09"),
                Transaction("OOO 'Enterprise'", "24.06.2024", stringResource(id = R.string.executed),"$11"),
                Transaction("OAO 'Company'", "24.06.2024", stringResource(id = R.string.executed),"$25.99")
            )
            AccountScreen(navController = navController, accounts = accounts, transactions = transactions)
        }
        composable(route = Screen.TransactionScreen.route) {
            TransactionScreen(navController = navController)
        }
        composable(route = Screen.AllTransactionsScreen.route) {
            AllTransactionsScreen(navController = navController)
        }
    }
}