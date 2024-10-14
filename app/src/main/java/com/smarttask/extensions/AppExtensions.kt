package com.smarttask.extensions

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

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