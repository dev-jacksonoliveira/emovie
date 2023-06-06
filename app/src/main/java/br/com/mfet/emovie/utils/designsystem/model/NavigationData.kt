package br.com.mfet.emovie.utils.designsystem.model


data class NavigationData(
    val toolbarTitle: String? = null,
    val hasBackButton: Boolean = true,
    val firstActionButton: NavigationButtonData? = null,
    val secondActionButton: NavigationButtonData? = null,
    val toolbarConfig: NavigationStyleConfigData? = null
)
