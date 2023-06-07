package br.com.mfet.emovie.utils.designsystem.model

import android.content.Context
import android.widget.TextView

data class ColorfulTextViewData(
    val textViewList: List<TextView>,
    val context: Context,
    val pressedColor: Int,
    val enabledColor: Int
)
