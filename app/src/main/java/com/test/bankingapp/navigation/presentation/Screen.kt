package com.test.bankingapp.navigation.presentation

sealed class Screen(val route: String) {
    object AccountScreen: Screen("account_screen")
    object TransactionScreen: Screen("transaction_screen")
    object AllTransactionsScreen: Screen("all_transactions_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}