package com.test.bankingapp.account.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.presentation.AccountViewModel
import com.test.bankingapp.account.presentation.util.AccountBottomSheetContent
import com.test.bankingapp.account.presentation.util.AccountItem
import com.test.bankingapp.navigation.presentation.Screen
import com.test.bankingapp.util.composable_items.RoundedLazyColumn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var selectedAccount by remember { mutableStateOf<Account?>(null) }
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isSheetVisible by remember { mutableStateOf(false) }

    val accounts by viewModel.accounts.collectAsState(initial = emptyList())
    val recentTransactions by viewModel.recentTransactions.collectAsState(initial = emptyList())

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
                    selectedAccount = Account(
                        title = account.accountName,
                        accountNumber = account.accountNumber.toString(),
                        debitCardNumber = account.cardNumber.toString()
                    )
                    coroutineScope.launch {
                        viewModel.fetchRecentTransactionsForAccount(account.accountNumber)
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

                selectedAccount?.let {
                    AccountItem(it) {
                        coroutineScope.launch {
                            bottomSheetState.show()
                            isSheetVisible = true
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (recentTransactions.isNotEmpty()) {
                    Text(
                        text = stringResource(id = R.string.recent_transactions),
                        color = colorResource(id = R.color.white),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    RoundedLazyColumn(
                        navController = navController,
                        transactions = recentTransactions
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.no_recent_transactions),
                        color = colorResource(id = R.color.white),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(navController = rememberNavController())
}