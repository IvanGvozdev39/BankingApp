package com.test.bankingapp.navigation.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.account.presentation.AccountViewModel
import com.test.bankingapp.account.presentation.screens.AccountScreen
import com.test.bankingapp.transaction.presentation.screens.AllTransactionsScreen
import com.test.bankingapp.transaction.presentation.screens.TransactionScreen

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.AccountScreen.route) {
        composable(route = Screen.AccountScreen.route) {
            val viewModel: AccountViewModel = hiltViewModel()
            AccountScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.TransactionScreen.route) {
            val viewModel: AccountViewModel = hiltViewModel()
            TransactionScreen(navController = navController)
        }
        composable(route = Screen.AllTransactionsScreen.route) {
            val viewModel: AccountViewModel = hiltViewModel()
            AllTransactionsScreen(navController = navController)
        }
    }
}
