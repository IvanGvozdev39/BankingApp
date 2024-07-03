package com.test.bankingapp.navigation.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.bankingapp.account.presentation.screens.AccountScreen
import com.test.bankingapp.account.presentation.screens.AddAccountScreen
import com.test.bankingapp.transaction.presentation.screens.AllTransactionsScreen
import com.test.bankingapp.transaction.presentation.screens.TransactionScreen
import com.test.bankingapp.transaction.presentation.viewmodel.TransactionViewModel
import com.test.bankingapp.util.Constants

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val viewModel: TransactionViewModel = hiltViewModel()
    val pref = context.getSharedPreferences(Constants.SHARED_PREF_S, Context.MODE_PRIVATE)

    NavHost(navController = navController, startDestination = Screen.AccountScreen.route) {
        composable(route = Screen.AccountScreen.route) {
            AccountScreen(navController = navController)
        }
        composable(route = Screen.TransactionScreen.route,
            arguments = listOf(
                navArgument("transactionId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getLong("transactionId") ?: -1L
            TransactionScreen(navController = navController, transactionId = transactionId)
        }
        composable(route = Screen.AllTransactionsScreen.route) {
            AllTransactionsScreen(navController = navController)
        }
        composable(route = Screen.AddAccountScreen.route) {
            AddAccountScreen(navController = navController)
        }
    }
}
