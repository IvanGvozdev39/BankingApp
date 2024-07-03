package com.test.bankingapp.transaction.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.transaction.presentation.viewmodel.AllTransactionsViewModel
import com.test.bankingapp.util.composable_items.RoundedLazyColumn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllTransactionsScreen(
    navController: NavController,
    viewModel: AllTransactionsViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isSheetVisible by remember { mutableStateOf(false) }
    val transactions by viewModel.transactions.collectAsState()
    var filteredTransactions by remember { mutableStateOf(emptyList<TransactionEntity>()) }
    var filterApplied by remember { mutableStateOf(false)}

    BackHandler(enabled = isSheetVisible) {
        coroutineScope.launch {
            bottomSheetState.hide()
            isSheetVisible = false
        }
    }

    viewModel.getAllTransactions()

    val handleOnFilterUpdate: (String, String) -> Unit = { startDate, endDate ->
        filterApplied = true
        filteredTransactions = filterTransactions(startDate, endDate, transactions)
    }

    ModalBottomSheetLayout(
        sheetContent = {
            TransactionBottomSheetContent(onDismissRequest = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    isSheetVisible = false
                }
            },
                onFilterApply = handleOnFilterUpdate,
                onFilterCleared = {
                    filterApplied = false
                }
            )},
        sheetState = bottomSheetState,
        scrimColor = if (isSheetVisible) Color.Black.copy(alpha = 0.6f) else Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.black))
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = stringResource(id = R.string.go_back),
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(28.dp)
                    )

                }
                Text(
                    text = stringResource(id = R.string.all_transactions),
                    color = colorResource(id = R.color.white),
                    fontSize = 20.sp
                )
                IconButton(onClick = {
                    coroutineScope.launch {
                        bottomSheetState.show()
                        isSheetVisible = true
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter_by_date),
                        contentDescription = stringResource(id = R.string.filter_by_date),
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(24.dp)
                    )

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            RoundedLazyColumn(navController = navController, transactions = if(filterApplied) filteredTransactions else transactions)
        }
    }

}

fun filterTransactions(startDate: String, endDate: String, transactions: List<TransactionEntity>): List<TransactionEntity> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startLocalDate = LocalDate.parse(startDate, formatter)
    val endLocalDate = LocalDate.parse(endDate, formatter)

    return transactions.filter { transaction ->
        val transactionDate = LocalDate.parse(transaction.date.toString(), formatter)
        transactionDate.isEqual(startLocalDate) || transactionDate.isEqual(endLocalDate) ||
                (transactionDate.isAfter(startLocalDate) && transactionDate.isBefore(endLocalDate))
    }
}


@Preview(showBackground = true)
@Composable
fun AllTransactionsScreenPreview() {
    AllTransactionsScreen(rememberNavController())
}