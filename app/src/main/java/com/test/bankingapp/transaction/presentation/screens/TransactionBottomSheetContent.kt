package com.test.bankingapp.transaction.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.bankingapp.R
import com.test.bankingapp.transaction.presentation.util.CustomDatePickerDialog
import com.test.bankingapp.util.Fonts
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TransactionBottomSheetContent(
    onDismissRequest: () -> Unit,
    onFilterApply: (String, String) -> Unit,
    onFilterCleared: () -> Unit
) {
    val context = LocalContext.current
    var endDateEarlierMessage = stringResource(id = R.string.enddate_cannot_be_before_startdate)

    var showStartDateCalendarDialog by remember { mutableStateOf(false) }
    var showEndDateCalendarDialog by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    var startDateRed by remember { mutableStateOf(false) }
    var endDateRed by remember { mutableStateOf(false) }

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.filter_by_date),
                color = colorResource(id = R.color.white),
                fontSize = 28.sp,
                fontFamily = Fonts.SF_SEMIBOLD_FONT,
                modifier = Modifier.padding(bottom = 16.dp, start = 8.dp)
            )
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxWidth())
            TextButton(onClick = {
                startDate = ""
                endDate = ""
                onFilterCleared()
            }) {
                Text(
                    text = stringResource(id = R.string.clear).uppercase(),
                    color = colorResource(id = R.color.blue),
                    fontSize = 13.sp,
                    fontFamily = Fonts.SF_LIGHT_FONT
                )
            }
        }

        Text(
            text = stringResource(id = R.string.start_date),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Fonts.SF_LIGHT_FONT
        )
        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(54.dp)
                .clickable {
                    showStartDateCalendarDialog = true
                },
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.White,
                disabledBorderColor = if (startDateRed) colorResource(id = R.color.red) else Color.White,
                disabledLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.white)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp)
        )

        if (showStartDateCalendarDialog) {
            CustomDatePickerDialog(
                selectedDate = LocalDate.now(),
                onDateSelected = {
                    startDate = it.toString()
                    showStartDateCalendarDialog = false
                },
                onDialogDismissed = {
                    showStartDateCalendarDialog = false
                }
            )
        }

        Text(
            text = stringResource(id = R.string.end_date),
            color = colorResource(id = R.color.white),
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            fontFamily = Fonts.SF_LIGHT_FONT
        )
        OutlinedTextField(
            value = endDate,
            onValueChange = { endDate = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .height(54.dp)
                .clickable {
                    showEndDateCalendarDialog = true
                },
            enabled = false,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.White,
                disabledBorderColor = if (endDateRed) colorResource(id = R.color.red) else Color.White,
                disabledLabelColor = Color.Gray,
                cursorColor = colorResource(id = R.color.white)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(10.dp)
        )

        if (showEndDateCalendarDialog) {
            CustomDatePickerDialog(
                selectedDate = LocalDate.now(),
                onDateSelected = {
                    endDate = it.toString()
                    showEndDateCalendarDialog = false
                },
                onDialogDismissed = {
                    showEndDateCalendarDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            thickness = 2.dp, color = colorResource(id = R.color.dark_gray),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                startDateRed = startDate.isEmpty()
                endDateRed = endDate.isEmpty()

                if (!startDateRed && !endDateRed) {
                    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val startLocalDate = LocalDate.parse(startDate, dateFormatter)
                    val endLocalDate = LocalDate.parse(endDate, dateFormatter)

                    if (endLocalDate.isBefore(startLocalDate)) {
                        Toast.makeText(
                            context,
                            endDateEarlierMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onFilterApply(startDate, endDate)
                        onDismissRequest()
                    }
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
            Text(text = stringResource(id = R.string.submit), fontSize = 16.sp, fontFamily = Fonts.SF_SEMIBOLD_FONT)
        }

    }
}