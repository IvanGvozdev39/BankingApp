package com.test.bankingapp.account.presentation.screens

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Account
import com.test.bankingapp.account.presentation.util.AccountBottomSheetContent
import com.test.bankingapp.account.presentation.util.AccountItem
import com.test.bankingapp.account.presentation.viewmodel.AccountViewModel
import com.test.bankingapp.navigation.presentation.Screen
import com.test.bankingapp.util.Constants
import com.test.bankingapp.util.composable_items.RoundedLazyColumn
import com.test.bankingapp.util.shared_preferences.SaveLong
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isSheetVisible by remember { mutableStateOf(false) }

    val accounts by viewModel.accounts.collectAsState()
    val selectedAccount by viewModel.selectedAccount.collectAsState()
    val recentTransactions by viewModel.recentTransactions.collectAsState()

    BackHandler(enabled = isSheetVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
            isSheetVisible = false
        }
    }

    Log.d("abcd123", "Selected account is ${selectedAccount.toString()}")
    selectedAccount?.let { viewModel.recentTransactionsForAccount(it.accountNumber) }

    ModalBottomSheetLayout(
        sheetContent = {
            AccountBottomSheetContent(
                accounts = accounts,
                onAccountSelected = { account ->
                    viewModel.selectAccount(account)
                    coroutineScope.launch {
                        viewModel.recentTransactionsForAccount(account.accountNumber)
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
                selectedAccount?.let {
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
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.black))
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.account),
                        color = colorResource(id = R.color.white),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Spacer(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                    TextButton(onClick = {
                        navController.navigate(Screen.AddAccountScreen.route)
                    }) {
                        Text(
                            text = stringResource(id = R.string.add_account).uppercase(),
                            color = colorResource(id = R.color.blue),
                            fontSize = 13.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                selectedAccount?.let {
                    SaveLong(key = Constants.PREF_SELECTED_ACCOUNT_ID_KEY, value = selectedAccount!!.accountNumber)
                    AccountItem(
                        Account(
                            title = it.accountName,
                            accountNumber = it.accountNumber.toString(),
                            debitCardNumber = it.cardNumber.toString()
                        )
                    ) {
                        coroutineScope.launch {
                            bottomSheetState.show()
                            isSheetVisible = true
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (recentTransactions.isNotEmpty()) {
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
                                text = stringResource(id = R.string.view_all).uppercase(),
                                color = colorResource(id = R.color.blue),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    RoundedLazyColumn(navController = navController, transactions = recentTransactions)
                } else {
                    Text(
                        text = stringResource(id = R.string.no_recent_transactions),
                        color = colorResource(id = R.color.light_gray),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
