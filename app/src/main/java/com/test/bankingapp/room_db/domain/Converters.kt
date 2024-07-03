package com.test.bankingapp.room_db.domain

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? {
        val dateString = date?.format(formatter)
        Log.d("abcd123", "Converting")
        return dateString
    }

    @TypeConverter
    @JvmStatic
    fun toLocalDate(dateString: String?): LocalDate? {
        val date = dateString?.let {
            LocalDate.parse(it, formatter)
        }
        Log.d("abcd123", "Converting")
        return date
    }
}
