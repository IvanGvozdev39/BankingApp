package com.test.bankingapp.transaction.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.test.bankingapp.R
import com.test.bankingapp.transaction.presentation.util.CustomDatePickerDialog
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TransactionScreen(navController: NavController) {
    var appliedIn by remember { mutableStateOf("") }
    var transactionNumber by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var transactionStatus by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val statusOptions = listOf(
        stringResource(id = R.string.executed),
        stringResource(id = R.string.in_progress),
        stringResource(id = R.string.declined)
    )
    var showCalendarDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
            modifier = Modifier.padding(bottom = 28.dp)
        )

        Text(
            text = stringResource(id = R.string.transaction_was_applied_in),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
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
                cursorColor = colorResource(id = R.color.white)
            ),
            shape = RoundedCornerShape(10.dp),
            textStyle = TextStyle(
                color = colorResource(id = R.color.white),
                fontSize = 16.sp
            )
        )

        Text(
            text = stringResource(id = R.string.transaction_number),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
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
                cursorColor = colorResource(id = R.color.white)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            shape = RoundedCornerShape(10.dp)
        )

        Text(
            text = stringResource(id = R.string.date),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(54.dp)
                .clickable {
                    showCalendarDialog = true
                },
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.White,
                disabledBorderColor = Color.White,
                disabledLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.white)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp)
        )

        if (showCalendarDialog) {
            CustomDatePickerDialog(
                selectedDate = LocalDate.now(),
                onDateSelected = {
                    date = it.toString()
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
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = transactionStatus,
                    onValueChange = { transactionStatus = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .height(54.dp),
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        disabledTextColor = Color.White,
                        disabledBorderColor = Color.White,
                        disabledLabelColor = Color.Gray,
                        cursorColor = colorResource(id = R.color.white)
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
            }

            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth(),
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
            fontSize = 18.sp
        )
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(62.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(id = R.color.white),
                focusedBorderColor = colorResource(id = R.color.white),
                unfocusedBorderColor = colorResource(id = R.color.white),
                cursorColor = colorResource(id = R.color.white)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Button(
            onClick = { /* TODO */ },
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
            Text(text = stringResource(id = R.string.okay), fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    TransactionScreen(rememberNavController())
}
