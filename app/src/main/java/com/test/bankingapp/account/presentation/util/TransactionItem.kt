package com.test.bankingapp.account.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Transaction

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = colorResource(id = R.color.dark_gray),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.title, color = colorResource(id = R.color.white), fontSize = 16.sp)
            Text(text = transaction.date, color = colorResource(id = R.color.light_gray), fontSize = 14.sp)
            TransactionStatus(transaction.status)
        }
        Text(text = transaction.amount, color = colorResource(id = R.color.white), fontSize = 16.sp)
        Image(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = stringResource(
            id = R.string.arrow_right
        ),
            modifier = Modifier.align(alignment = Alignment.CenterVertically))
    }
}

@Composable
fun TransactionStatus(status: String) {
    val color = when (status) {
        stringResource(id = R.string.executed) -> colorResource(id = R.color.green)
        stringResource(id = R.string.in_progress) -> colorResource(id = R.color.yellow)
        stringResource(id = R.string.declined) -> colorResource(id = R.color.red)
        else -> colorResource(id = R.color.white)
    }
    Text(text = status, color = color, fontSize = 14.sp)
}