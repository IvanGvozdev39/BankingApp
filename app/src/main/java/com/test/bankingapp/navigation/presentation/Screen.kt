package com.test.bankingapp.navigation.presentation

sealed class Screen(val route: String) {
    object AccountScreen: Screen("account_screen")
    object TransactionScreen: Screen("transaction_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}