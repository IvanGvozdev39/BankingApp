package com.test.bankingapp.account.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.test.bankingapp.account.presentation.viemodel.AddAccountViewModel
import com.test.bankingapp.room_db.domain.models.AccountEntity
import java.util.UUID

@Composable
fun AddAccountScreen(
    navController: NavController,
    viewModel: AddAccountViewModel = hiltViewModel()
) {
    var accountName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    val context = LocalContext.current
    val successMessage = stringResource(id = R.string.account_created_successfully)
    val cardNumber8to19DigitsMessage = stringResource(id = R.string.card_number_must_consist_of_8_to_19_digits)
    val nameCannotBeEmptyMessage = stringResource(id = R.string.account_name_cannot_be_empty)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.add_account),
                color = colorResource(id = R.color.white),
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 28.dp)
            )

            Text(
                text = stringResource(id = R.string.account_name),
                color = colorResource(id = R.color.white),
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = accountName,
                onValueChange = { accountName = it },
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
                text = stringResource(id = R.string.card_number),
                color = colorResource(id = R.color.white),
                fontSize = 18.sp
            )
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(54.dp),
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
        }

        Button(
            onClick = {
                if (accountName.trim().isEmpty()) {
                    Toast.makeText(context, nameCannotBeEmptyMessage, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (!cardNumber.all { it.isDigit() } || cardNumber.length < 8 || cardNumber.length > 19) {
                    Toast.makeText(context, cardNumber8to19DigitsMessage, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                //13 digit account number
                val uuid = UUID.randomUUID()
                val digitsOnly = uuid.toString().filter { it.isDigit() }
                viewModel.addAccount(AccountEntity(
                    accountName = accountName,
                    accountNumber = digitsOnly.take(13).toLong(),
                    cardNumber = cardNumber.toLong()))

                Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.blue),
                contentColor = colorResource(id = R.color.white)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = stringResource(id = R.string.create), fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AddAccountScreen(rememberNavController())
}
