package br.com.mfet.emovie.utils.designsystem.model

import androidx.annotation.StringRes

data class NavigationButtonData(
    @StringRes val icon: Int? = null,
    val textAccessibility: String? = null,
    val action: (() -> Unit)? = null
)
