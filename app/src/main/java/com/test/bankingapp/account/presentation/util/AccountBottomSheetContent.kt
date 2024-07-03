package com.test.bankingapp.account.presentation.util

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.bankingapp.R
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.util.Constants
import com.test.bankingapp.util.Fonts
import com.test.bankingapp.util.composable_items.CustomAlertDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountBottomSheetContent(
    accounts: List<AccountEntity>,
    onAccountSelected: (AccountEntity) -> Unit,
    onAccountDelete: (AccountEntity) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedForDeletionAccount by remember { mutableStateOf<AccountEntity?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

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
        Text(
            text = stringResource(id = R.string.select_account),
            color = colorResource(id = R.color.white),
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 16.dp, start = 8.dp),
            fontFamily = Fonts.SF_SEMIBOLD_FONT
        )

        LazyColumn {
            items(accounts) { account ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            color = colorResource(id = R.color.dark_gray),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                        .combinedClickable(
                            onClick = {
                                onAccountSelected(account)
                                onDismissRequest()
                            },
                            onLongClick = {
                                selectedForDeletionAccount = account
                                showDeleteDialog = true
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.apple_mastercard),
                        contentDescription = stringResource(id = R.string.account_image),
                        modifier = Modifier
                            .width(52.dp)
                            .height(25.dp)
                            .padding(top = 5.dp, end = 10.dp)
                    )
                    Column {
                        Text(
                            text = account.accountName,
                            color = colorResource(id = R.color.white),
                            fontSize = 16.sp,
                            fontFamily = Fonts.SF_SEMIBOLD_FONT
                        )
                        Text(
                            text = account.accountNumber.toString(),
                            color = colorResource(id = R.color.light_gray),
                            fontSize = 14.sp
                        )
                        Text(
                            text = account.cardNumber.toString(),
                            color = colorResource(id = R.color.light_gray),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        if (showDeleteDialog) {
            CustomAlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = {
                    Text(
                        text = stringResource(id = R.string.are_you_sure_delete_account),
                        color = colorResource(id = R.color.white),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        selectedForDeletionAccount?.let { onAccountDelete(it) }
                        showDeleteDialog = false
                        if (accounts.isEmpty())
                            onDismissRequest()
                    }) {
                        Text(
                            text = stringResource(id = R.string.yes),
                            color = colorResource(id = R.color.white),
                            fontSize = 16.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text(
                            text = stringResource(id = R.string.no),
                            color = colorResource(id = R.color.white),
                            fontSize = 16.sp
                        )
                    }
                }
            )
        }
    }
}
