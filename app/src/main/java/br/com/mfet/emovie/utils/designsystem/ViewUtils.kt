package br.com.mfet.emovie.utils.designsystem

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import br.com.mfet.emovie.utils.designsystem.model.ColorfulTextViewData

fun getDimension(@DimenRes res: Int, context: Context): Int {
    return context.resources.getDimension(res).toInt()
}

fun getColor(color: Int, context: Context): Int {
    return ContextCompat.getColor(context, color)
}

internal fun setTextViewColorStateList(colorfulTextViewData: ColorfulTextViewData) {
    with(colorfulTextViewData) {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_pressed),
            intArrayOf(android.R.attr.state_enabled)
        )

        val colors = intArrayOf(
            ContextCompat.getColor(context, pressedColor),
            ContextCompat.getColor(context, enabledColor)
        )

        val list = ColorStateList(states, colors)

        textViewList.forEach {
            it.setTextColor(list)
        }
    }
}

fun getDrawable(context: Context, background: Int): Drawable? {
    return ContextCompat.getDrawable(
        context, background
    )
}

fun isDarkMode(): Boolean = DISABLE_DARK_MODE

private const val DISABLE_DARK_MODE = false