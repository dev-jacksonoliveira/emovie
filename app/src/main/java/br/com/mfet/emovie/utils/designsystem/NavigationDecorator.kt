package br.com.mfet.emovie.utils.designsystem

import android.view.Gravity
import br.com.mfet.emovie.databinding.NavigationViewBinding
import br.com.mfet.emovie.utils.designsystem.model.NavigationStyleConfigData

class NavigationDecorator(private val binding: NavigationViewBinding) {
    private val context = binding.root.context

    fun setUpToolbar(navigationStyle: NavigationStyleConfigData) {
        var style = navigationStyle
        if (isDarkMode())
            style = NavigationColor.DARK_MODE.style

        with(style) {
            iconColor?.let { color ->
                setIconColors(color)
            }
            backgroundColor?.let { color ->
                binding.navRoot.setBackgroundColor(
                    getColor(
                        color,
                        context
                    )
                )
            }
            titleColor?.let { color -> binding.navTitle.setTextColor(getColor(color, context)) }
        }
    }

    fun changeTitleAlignment() {
        binding.navTitle.gravity = Gravity.START
    }

    private fun setIconColors(iconColor: Int) = with(binding) {
        val color = getColor(iconColor, context)

        navBackButton.setTextColor(color)
        navFirstButton.setTextColor(color)
        navSecondButton.setTextColor(color)
    }
}