package com.test.bankingapp.transaction.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.account.domain.model.Transaction
import com.test.bankingapp.transaction.presentation.util.CustomDatePickerDialog
import com.test.bankingapp.util.composable_items.RoundedLazyColumn
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllTransactionsScreen(navController: NavController) {
    val transactions = listOf(
        Transaction("OOO 'Company'", "26.06.2024", stringResource(id = R.string.in_progress),"$10.09"),
        Transaction("OOO 'Enterprise'", "24.06.2024", stringResource(id = R.string.executed),"$11"),
        Transaction("OAO 'Syndicate'", "24.06.2024", stringResource(id = R.string.executed),"$25.99")
    )
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var isSheetVisible by remember { mutableStateOf(false) }

    ModalBottomSheetLayout(
        sheetContent = {
            TransactionBottomSheetContent(onDismissRequest = {
                coroutineScope.launch {
                    bottomSheetState.hide()
                    isSheetVisible = false
                }
            })
        },
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
            RoundedLazyColumn(navController = navController, transactions = transactions)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AllTransactionsScreenPreview() {
    AllTransactionsScreen(rememberNavController())
}