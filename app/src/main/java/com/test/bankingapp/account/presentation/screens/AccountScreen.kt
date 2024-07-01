package com.test.bankingapp.account.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.domain.model.Transaction
import com.test.bankingapp.account.presentation.util.AccountBottomSheetContent
import com.test.bankingapp.account.presentation.util.AccountItem
import com.test.bankingapp.navigation.presentation.Screen
import com.test.bankingapp.util.composable_items.RoundedLazyColumn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    navController: NavController,
    accounts: List<Account>,
    transactions: List<Transaction>
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var selectedAccount by remember { mutableStateOf<Account?>(null) }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isSheetVisible by remember { mutableStateOf(false) }

    BackHandler(enabled = isSheetVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
            isSheetVisible = false
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            AccountBottomSheetContent(
                accounts = accounts,
                onAccountSelected = { account ->
                    selectedAccount = account
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        isSheetVisible = false
                    }
                },
                onDismissRequest = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        isSheetVisible = false
                    }
                }
            )
        },
        sheetState = bottomSheetState,
        scrimColor = if (isSheetVisible) Color.Black.copy(alpha = 0.6f) else Color.Transparent
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.TransactionScreen.route)
                    },
                    backgroundColor = colorResource(id = R.color.blue),
                    contentColor = colorResource(id = R.color.white)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        modifier = Modifier.size(30.dp)
                    )
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

                AccountItem(accounts[0]) {   //todo: change to actual selected account
                    coroutineScope.launch {
                        bottomSheetState.show()
                        isSheetVisible = true
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
                            .fillMaxWidth()
                    )
                    TextButton(onClick = {
                        navController.navigate(Screen.AllTransactionsScreen.route)
                    }) {
                        Text(
                            text = stringResource(id = R.string.view_all),
                            color = colorResource(id = R.color.blue),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                RoundedLazyColumn(navController = navController, transactions = transactions)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    val accounts = listOf(
        Account("Saving Account", "9121219291221", "**** 1234"),
        Account("My first account", "9121219291221", "**** 1234"),
        Account("For travelling", "9121219291221", "**** 1234")
    )
    val transactions = listOf(
        Transaction(
            "OOO 'Company'",
            "UPI/2323232323/TRASANCTION",
            stringResource(id = R.string.executed),
            "$10.09"
        ),
        Transaction(
            "OOO 'Company'",
            "UPI/2323232323/TRASANCTION",
            stringResource(id = R.string.declined),
            "$10.09"
        )
    )
    AccountScreen(
        navController = rememberNavController(),
        accounts = accounts,
        transactions = transactions
    ) //todo: remove later
}