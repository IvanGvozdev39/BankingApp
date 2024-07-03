package com.test.bankingapp.util

fun formatAmount(amount: Float): String {
    return if (amount % 1 == 0f) {
        "$${amount.toInt()}"
    } else {
        "$${"%.2f".format(amount)}"
    }
}
