package com.smarttask.extensions

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Fragment.navigateTo(actionId: NavDirections) {
    findNavController().navigate(actionId)
}

fun Context.getColorResource(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun String.toFormattedDateAndDaysLeft(targetDate: String): Pair<String, String> {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val dueDate: Date = inputFormat.parse(this)!!
    val targetDateParsed: Date = inputFormat.parse(targetDate)!!
    val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH)
    val formattedDueDate = outputFormat.format(dueDate)
    val diffInMillis = dueDate.time - targetDateParsed.time
    val daysLeft = diffInMillis / (1000 * 60 * 60 * 24)
    return Pair(formattedDueDate, daysLeft.toString())
}