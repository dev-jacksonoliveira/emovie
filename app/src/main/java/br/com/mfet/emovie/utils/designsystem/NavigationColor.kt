package br.com.mfet.emovie.utils.designsystem

import br.com.mfet.emovie.R
import br.com.mfet.emovie.utils.designsystem.model.NavigationStyleConfigData

enum class NavigationColor(val style: NavigationStyleConfigData) {
    DARK_MODE(
        NavigationStyleConfigData(
            backgroundColor = R.color.black_100,
            iconColor = R.color.yellow,
            titleColor = R.color.yellow
        )
    ),
    WHITE(
        NavigationStyleConfigData(
            backgroundColor = R.color.white,
            iconColor = R.color.black_100,
            titleColor = R.color.black_100
        )
    );

    companion object {
        fun getItemByOrdinal(ordinal: Int): NavigationColor {
            if (ordinal < values().size)
                return values()[ordinal]
            return WHITE
        }
    }

}
