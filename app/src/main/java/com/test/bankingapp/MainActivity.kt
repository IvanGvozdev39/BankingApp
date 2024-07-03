package com.test.bankingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.test.bankingapp.navigation.presentation.Navigation
import com.test.bankingapp.room_db.domain.AccountTransactionDatabase
import com.test.bankingapp.room_db.domain.models.AccountEntity
import com.test.bankingapp.room_db.domain.models.TransactionEntity
import com.test.bankingapp.ui.theme.BankingAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BankingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(this)
                }
            }
        }
    }
}