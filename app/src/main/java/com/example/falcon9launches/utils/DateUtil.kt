package com.example.falcon9launches.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtil{

    @SuppressLint("SimpleDateFormat")
    fun formatDate(dateString: String): String {
        return if (dateString.isEmpty().not()) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("dd-MM-yyyy")
            val date: Date? = inputFormat.parse(dateString)
            outputFormat.format(date!!) ?: ""
        } else
            ""
    }

}
