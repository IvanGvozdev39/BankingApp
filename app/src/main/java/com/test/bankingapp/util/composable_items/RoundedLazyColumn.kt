package com.test.bankingapp.util.composable_items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Transaction
import com.test.bankingapp.account.presentation.util.TransactionItem

@Composable
fun RoundedLazyColumn(navController: NavController, transactions: List<Transaction>) {
    Box(
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.dark_gray),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(transactions.size) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    TransactionItem(transaction = transactions[index],
                        navController = navController)
                    // Add a divider if it's not the last item
                    if (index < transactions.size - 1) {
                        Divider(
                            color = colorResource(id = R.color.gray),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}