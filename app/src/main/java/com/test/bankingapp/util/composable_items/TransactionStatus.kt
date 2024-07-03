package com.test.bankingapp.util.composable_items

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.test.bankingapp.R

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