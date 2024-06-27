package com.test.bankingapp.account.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.test.bankingapp.account.domain.model.Account

@Composable
fun AccountItem(account: Account, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()
        .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(8.dp)
                .background(
                    color = colorResource(id = R.color.dark_gray),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.apple_mastercard),
                contentDescription = stringResource(id = R.string.account_image),
                modifier = Modifier
                    .width(52.dp)
                    .height(25.dp)
                    .padding(top = 5.dp, end = 10.dp)
            )
            Column {
                Text(
                    text = account.title,
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp
                )
                Text(
                    text = account.accountNumber,
                    color = colorResource(id = R.color.light_gray),
                    fontSize = 14.sp
                )
                Text(
                    text = account.debitCardNumber,
                    color = colorResource(id = R.color.light_gray),
                    fontSize = 14.sp
                )
            }
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = stringResource(id = R.string.arrow_right),
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
    }
}