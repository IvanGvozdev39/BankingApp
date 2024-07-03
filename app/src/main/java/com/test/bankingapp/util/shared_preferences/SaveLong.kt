package com.test.bankingapp.util.shared_preferences

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.test.bankingapp.util.Constants

@Composable
fun SaveLong(key: String, value: Long) {
    val pref = LocalContext.current.getSharedPreferences(Constants.SHARED_PREF_S, Context.MODE_PRIVATE)
    pref.edit().putLong(key, value).apply()
}