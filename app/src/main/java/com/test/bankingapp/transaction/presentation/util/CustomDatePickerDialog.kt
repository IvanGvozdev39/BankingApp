package com.test.bankingapp.transaction.presentation.util

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.test.bankingapp.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

@Composable
fun CustomDatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDialogDismissed: () -> Unit
) {
    var datePickerExpanded by remember { mutableStateOf(true) }
    val currentDate = LocalDate.now()
    var displayedMonth by remember { mutableStateOf(currentDate.month) }
    var displayedYear by remember { mutableStateOf(currentDate.year) }

    if (datePickerExpanded) {
        BackHandler {
            datePickerExpanded = false
        }

        Dialog(onDismissRequest = { datePickerExpanded = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = colorResource(id = R.color.dark_gray)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${
                                displayedMonth.name.lowercase().replaceFirstChar { it.uppercase() }
                            } $displayedYear",
                            color = colorResource(id = R.color.white),
                            fontSize = 18.sp
                        )
                        Row {
                            IconButton(onClick = {
                                displayedMonth = displayedMonth.minus(1)
                                if (displayedMonth == Month.DECEMBER) {
                                    displayedYear -= 1
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_left),
                                    contentDescription = stringResource(id = R.string.previous_month),
                                    tint = colorResource(id = R.color.white)
                                )
                            }
                            IconButton(onClick = {
                                displayedMonth = displayedMonth.plus(1)
                                if (displayedMonth == Month.JANUARY) {
                                    displayedYear += 1
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_right),
                                    contentDescription = stringResource(id = R.string.next_month),
                                    tint = colorResource(id = R.color.white)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        DayOfWeek.values().forEach { day ->
                            Text(
                                text = day.name.take(3),
                                color = colorResource(id = R.color.light_gray),
                                fontSize = 14.sp
                            )
                        }
                    }
                    val daysInMonth = YearMonth.of(displayedYear, displayedMonth).lengthOfMonth()
                    val firstDayOfMonth = LocalDate.of(displayedYear, displayedMonth, 1).dayOfWeek
                    val emptyDays = firstDayOfMonth.value % 7

                    Spacer(modifier = Modifier.height(10.dp))
                    Column {
                        for (weekIndex in 0 until 6) {
                            Row {
                                for (dayIndex in 0 until 7) {
                                    val dayNumber = dayIndex + 1 + weekIndex * 7 - emptyDays
                                    if (dayNumber > 0 && dayNumber <= daysInMonth) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clickable {
                                                    val selected = LocalDate.of(
                                                        displayedYear,
                                                        displayedMonth,
                                                        dayNumber
                                                    )
                                                    onDateSelected(selected)
                                                    datePickerExpanded = false
                                                }
                                                .padding(4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = dayNumber.toString(),
                                                color =
                                                if (currentDate.year == displayedYear &&
                                                    currentDate.month == displayedMonth &&
                                                    currentDate.dayOfMonth == dayNumber
                                                )
                                                    colorResource(id = R.color.blue)
                                                else colorResource(id = R.color.white),
                                                fontSize = 18.sp
                                            )
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                }
            }
        }
    }
}
