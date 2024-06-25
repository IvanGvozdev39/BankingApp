package com.test.bankingapp.account.domain.model

data class Transaction(
    val title: String,
    val date: String,
    val status: String,
    val amount: String
)