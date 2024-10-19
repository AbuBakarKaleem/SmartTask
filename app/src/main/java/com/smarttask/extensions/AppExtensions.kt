package com.smarttask.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Fragment.navigateTo(actionId: NavDirections) {
    findNavController().navigate(actionId)
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

fun Context.getColorResource(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getColorStateListCompat(colorRes: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, colorRes)
}

fun Context.getDrawableCompat(drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

fun <T> Fragment.setNavigationResult(key: String, value: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(
        key, value
    )
    val checkResult = findNavController().previousBackStackEntry?.savedStateHandle?.get<T>(key)
    Log.d("NavigationResult", "Result is set: $checkResult")
}

fun <T> Fragment.getNavigationResult(@IdRes id: Int, key: String, onResult: (result: T) -> Unit) {
    val navBackStackEntry = findNavController().getBackStackEntry(id)

    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_RESUME && navBackStackEntry.savedStateHandle.contains(key)) {
            val result = navBackStackEntry.savedStateHandle.get<T>(key)
            result?.let(onResult)
            navBackStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    navBackStackEntry.lifecycle.addObserver(observer)

    viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            navBackStackEntry.lifecycle.removeObserver(observer)
        }
    })
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

fun Fragment.longToast(message: String) =
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

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

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        dateFormat.parse(this)
    } catch (e: Exception) {
        null
    }
}