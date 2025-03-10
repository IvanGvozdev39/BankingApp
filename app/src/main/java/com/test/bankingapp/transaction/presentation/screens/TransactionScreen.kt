package com.test.bankingapp.transaction.presentation.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.transaction.presentation.util.CustomDatePickerDialog
import com.test.bankingapp.transaction.presentation.viewmodel.TransactionViewModel
import com.test.bankingapp.util.Constants
import com.test.bankingapp.util.Fonts
import java.time.LocalDate

@Composable
fun TransactionScreen(
    navController: NavController,
    transactionId: Long = -1,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val pref = context.getSharedPreferences(Constants.SHARED_PREF_S, Context.MODE_PRIVATE)
    val selectedAccountId = pref.getLong(Constants.PREF_SELECTED_ACCOUNT_ID_KEY, -1)

    val transaction by viewModel.transaction.collectAsState(initial = null)

    LaunchedEffect(transactionId) {
        if (selectedAccountId != -1L && transactionId != -1L) {
            viewModel.getTransactionById(selectedAccountId, transactionId)
        }
    }

    var appliedIn by remember { mutableStateOf(transaction?.appliedIn ?: "") }
    var transactionNumber by remember { mutableStateOf(transaction?.number?.toString() ?: "") }
    var date by remember { mutableStateOf(transaction?.date?.toString() ?: "") }
    var localDate by remember { mutableStateOf(transaction?.date) }
    var transactionStatus by remember { mutableStateOf(transaction?.status ?: "") }
    var amount by remember { mutableStateOf(transaction?.amount?.toString() ?: "") }

    LaunchedEffect(transaction) {
        transaction?.let {
            appliedIn = it.appliedIn
            transactionNumber = it.number.toString()
            date = it.date.toString()
            localDate = it.date
            transactionStatus = it.status
            amount = it.amount.toString()
        }
    }

    val statusOptions = listOf(
        stringResource(id = R.string.executed),
        stringResource(id = R.string.in_progress),
        stringResource(id = R.string.declined)
    )
    var showCalendarDialog by remember { mutableStateOf(false) }
    val successMessage = stringResource(id = R.string.transaction_created_successfully)
    val appliedInMessge = stringResource(id = R.string.applied_in_field_cannot_be_empty)
    val transactionNumberMessage = stringResource(id = R.string.transaction_number_must_be_a_number)
    val dateMessage = stringResource(id = R.string.transaction_date_not_set)
    val transactionStatusMessage = stringResource(id = R.string.transaction_status_not_set)
    val amountMessage = stringResource(id = R.string.amount_cannot_be_empty)
    
    val isEditable = transaction == null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.transaction),
            color = colorResource(id = R.color.white),
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 28.dp),
            fontFamily = Fonts.SF_SEMIBOLD_FONT
        )

        Text(
            text = stringResource(id = R.string.transaction_was_applied_in),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Fonts.SF_LIGHT_FONT
        )
        OutlinedTextField(
            value = appliedIn,
            onValueChange = { appliedIn = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(54.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                unfocusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                disabledTextColor = Color.White,
                disabledBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            ),
            enabled = isEditable,
            singleLine = true
        )

        Text(
            text = stringResource(id = R.string.transaction_number),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Fonts.SF_LIGHT_FONT
        )
        OutlinedTextField(
            value = transactionNumber,
            onValueChange = { transactionNumber = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(54.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                unfocusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                disabledTextColor = Color.White,
                disabledBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(10.dp),
            enabled = isEditable,
            singleLine = true
        )

        Text(
            text = stringResource(id = R.string.date),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Fonts.SF_LIGHT_FONT
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(54.dp)
                .clickable { if (isEditable) showCalendarDialog = true },
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.White,
                disabledBorderColor = if(isEditable) Color.White else colorResource(id = R.color.light_gray),
                disabledLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.white)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
        )

        if (showCalendarDialog) {
            CustomDatePickerDialog(
                selectedDate = LocalDate.now(),
                onDateSelected = {
                    date = it.toString()
                    localDate = it
                    showCalendarDialog = false
                },
                onDialogDismissed = {
                    showCalendarDialog = false
                }
            )
        }

        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.transaction_status),
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontFamily = Fonts.SF_LIGHT_FONT
                )
                OutlinedTextField(
                    value = transactionStatus,
                    onValueChange = { transactionStatus = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { if (isEditable) expanded = !expanded }
                        .height(54.dp),
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.white),
                        disabledTextColor = Color.White,
                        disabledBorderColor = if(isEditable) Color.White else colorResource(id = R.color.light_gray),
                        disabledLabelColor = Color.Gray,
                        cursorColor = colorResource(id = R.color.white)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )
            }

            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    statusOptions.forEach { status ->
                        DropdownMenuItem(onClick = {
                            transactionStatus = status
                            expanded = false
                        }) {
                            Text(text = status)
                        }
                    }
                }
            }
        }

        Text(
            text = stringResource(id = R.string.amount),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            fontFamily = Fonts.SF_LIGHT_FONT
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(54.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                unfocusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white),
                disabledTextColor = Color.White,
                disabledBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = isEditable,
            singleLine = true
        )

        Button(
            onClick = {
                if (isEditable) {
                    if (appliedIn.trim().isEmpty()) {
                        Toast.makeText(context, appliedInMessge, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (transactionNumber.trim().isEmpty() || !transactionNumber.all { it.isDigit() }) {
                        Toast.makeText(context, transactionNumberMessage, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (date.isEmpty()) {
                        Toast.makeText(context, dateMessage, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (transactionStatus.trim().isEmpty()) {
                        Toast.makeText(context, transactionStatusMessage, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (amount.trim().isEmpty() || !amount.matches("""^\d+(\.\d{1,2})?$""".toRegex())) {
                        Toast.makeText(context, amountMessage, Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    amount = formatFractionalPart(amount)

                    val accountId = pref.getLong(Constants.PREF_SELECTED_ACCOUNT_ID_KEY, 0)
                    if (localDate != null) {
                        val newTransaction = TransactionEntity(
                            appliedIn = appliedIn,
                            number = transactionNumber.toLongOrNull() ?: 0,
                            date = localDate!!,
                            status = transactionStatus,
                            amount = amount.toFloatOrNull() ?: 0f,
                            accountId = accountId
                        )
                        viewModel.addTransaction(newTransaction)
                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                } else {
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = if (isEditable) stringResource(id = R.string.save) else stringResource(id = R.string.okay),
                fontSize = 16.sp,
                fontFamily = Fonts.SF_SEMIBOLD_FONT
            )
        }
    }
}

fun formatFractionalPart(amount: String): String {
    return if (amount.contains('.')) {
        val parts = amount.split('.')
        if (parts[1].length == 1) {
            "${parts[0]}.${parts[1]}0"
        } else {
            amount
        }
    } else {
        "$amount.00"
    }
}


@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    TransactionScreen(rememberNavController())
}
