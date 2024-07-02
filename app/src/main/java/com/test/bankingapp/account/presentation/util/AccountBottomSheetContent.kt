package com.test.bankingapp.account.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.bankingapp.R
import com.test.bankingapp.room_db.domain.models.AccountEntity

@Composable
fun AccountBottomSheetContent(
    accounts: List<AccountEntity>,
    onAccountSelected: (AccountEntity) -> Unit,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.black))
            .padding(16.dp)
    ) {
        Divider(
            thickness = 6.dp, color = colorResource(id = R.color.dark_gray),
            modifier = Modifier
                .width(45.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.select_account),
            color = colorResource(id = R.color.white),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, start = 8.dp)
        )

        LazyColumn {
            items(accounts) { account ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = colorResource(id = R.color.dark_gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .clickable {
                            onAccountSelected(account)
                            onDismissRequest()
                        }
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
                            text = account.accountName,
                            color = colorResource(id = R.color.white),
                            fontSize = 16.sp
                        )
                        Text(
                            text = account.accountNumber.toString(),
                            color = colorResource(id = R.color.light_gray),
                            fontSize = 14.sp
                        )
                        Text(
                            text = account.cardNumber.toString(),
                            color = colorResource(id = R.color.light_gray),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}