package com.test.bankingapp.account.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.domain.model.Transaction


@Composable
fun AccountScreen(accounts: List<Account>, transactions: List<Transaction>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add),
                    modifier = Modifier.size(30.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.black))
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.account),
                color = colorResource(id = R.color.white),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(accounts) { account ->
                    AccountItem(account)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)

            ) {
                Text(
                    text = stringResource(id = R.string.recent_transactions),
                    color = colorResource(id = R.color.white),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth())
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = stringResource(id = R.string.view_all), color = colorResource(id = R.color.blue),
                        fontSize = 13.sp, modifier = Modifier.padding(bottom = 10.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
fun AccountItem(account: Account) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = colorResource(id = R.color.dark_gray),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.apple_mastercard), contentDescription = stringResource(
            id = R.string.account_image
        ),
            modifier = Modifier
                .width(52.dp)
                .height(25.dp)
                .padding(top = 5.dp, end = 10.dp))
        Column {
            Text(text = account.title, color = colorResource(id = R.color.white), fontSize = 16.sp)
            Text(text = account.accountNumber, color = colorResource(id = R.color.light_gray), fontSize = 14.sp)
            Text(text = account.debitCardNumber, color = colorResource(id = R.color.light_gray), fontSize = 14.sp)
        }
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxHeight())
        Image(painter = painterResource(id = R.drawable.ic_arrow_right), contentDescription = stringResource(
            id = R.string.arrow_right
        ),
            modifier = Modifier.align(alignment = Alignment.CenterVertically))
    }
}

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

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    val accounts = listOf(
        Account("Saving Account", "9121219291221", "**** 1234")
    )
    val transactions = listOf(
        Transaction("OOO \"Company\"", "01.06.2024", "Executed", "$10.09"),
        Transaction("OOO \"Company2\"", "02.06.2024", "Declined", "$10.09"),
        Transaction("OOO \"Company\"", "06.06.2024", "In progress", "$10.09"),
        Transaction("OOO \"Company\"", "01.06.2024", "Executed", "$10.09")
    )
    AccountScreen(accounts, transactions)
}
