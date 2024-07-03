package com.test.bankingapp.util.composable_items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.test.bankingapp.R

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = title,
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        backgroundColor = colorResource(id = R.color.dark_gray),
        shape = RoundedCornerShape(16.dp)
    )
}