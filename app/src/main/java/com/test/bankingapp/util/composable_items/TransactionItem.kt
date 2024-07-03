package com.test.bankingapp.util.composable_items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.test.bankingapp.R
import com.test.bankingapp.navigation.presentation.Screen
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.util.formatAmount

@Composable
fun TransactionItem(transaction: TransactionEntity, navController: NavController) {
    Box(modifier = Modifier.fillMaxWidth()
        .clickable {
            navController.navigate(
                route = Screen.TransactionScreen.passTransactionId(
                    transaction.number
                )
            )
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.dark_gray),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (transaction.appliedIn.length < 15) transaction.appliedIn
                    else transaction.appliedIn.take(12) + "...",
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp
                )
                Text(
                    text = transaction.date.toString(),
                    color = colorResource(id = R.color.light_gray),
                    fontSize = 14.sp
                )
                TransactionStatus(transaction.status)
            }
            Text(
                text = if (transaction.amount.toString().length < 10) formatAmount(transaction.amount)
                else formatAmount(transaction.amount).take(10) + "...",
                color = colorResource(id = R.color.white),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Top)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = stringResource(id = R.string.arrow_right),
                modifier = Modifier.align(alignment = Alignment.Top)
            )
        }
    }
}
