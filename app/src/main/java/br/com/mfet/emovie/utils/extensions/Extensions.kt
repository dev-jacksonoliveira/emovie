package br.com.mfet.emovie.utils.extensions

import android.app.Activity
import android.os.Build
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

object Extensions {

    fun Activity.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

fun TextView.setAccessibleTextView(labelValue: String, accessibilityValue: String = labelValue) {
    this.apply {
        isVisible = true
        text = labelValue
        setAccessibility(accessibilityValue)
    }
}

fun TextView.setAccessibility(textAccessibility: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.importantForAccessibility = ViewGroup.IMPORTANT_FOR_ACCESSIBILITY_YES
        this.contentDescription = textAccessibility
    }
}