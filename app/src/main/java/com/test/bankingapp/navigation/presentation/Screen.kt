package com.test.bankingapp.navigation.presentation

sealed class Screen(val route: String) {
    object AccountScreen: Screen("account_screen")
    object TransactionScreen: Screen("transaction_screen?transactionId={transactionId}") {
        fun passTransactionId(transactionId: Long): String {
            return "transaction_screen?transactionId=$transactionId"
        }
    }
    object AllTransactionsScreen: Screen("all_transactions_screen")
    object AddAccountScreen: Screen("add_account_screen")
}
