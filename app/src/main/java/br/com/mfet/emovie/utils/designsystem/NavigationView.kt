package br.com.mfet.emovie.utils.designsystem

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import br.com.mfet.emovie.R
import br.com.mfet.emovie.databinding.NavigationViewBinding
import br.com.mfet.emovie.utils.designsystem.model.NavigationButtonData
import br.com.mfet.emovie.utils.designsystem.model.NavigationData
import br.com.mfet.emovie.utils.designsystem.model.NavigationStyleConfigData
import br.com.mfet.emovie.utils.extensions.setAccessibility
import br.com.mfet.emovie.utils.extensions.setAccessibleTextView

@Suppress("TooManyFunctions")
class NavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleRes: Int = 0
) : CardView(context, attrs, defStyleRes) {

    private val binding = NavigationViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private val decorator: NavigationDecorator by lazy {
        NavigationDecorator(binding)
    }

    private lateinit var attributes: TypedArray

    init {
        attrs?.let { attributeSet ->
            fillAttributes(attributeSet)
        }
    }

    fun setUp(data: NavigationData) {
        with(data) {
            setToolbarTitle(toolbarTitle)
            enableBackButton(hasBackButton)
            firstActionButton?.let { button -> setFirstActionButton(button) }
            secondActionButton?.let { button -> setSecondActionButton(button) }
            toolbarConfig?.let { decorator.setUpToolbar(it) }
        }
    }

    fun setToolbarTitle(titleText: String?) = with(binding) {
        titleText?.let { value ->
            navTitle.setAccessibleTextView(value)
        }
    }

    fun setFirstActionButton(button: NavigationButtonData) {
        binding.navFirstButton.setClickableTextView(button)
    }

    fun setSecondActionButton(button: NavigationButtonData) {
        if (binding.navFirstButton.isInvisible)
            throw IllegalArgumentException(EXCEPTION)

        binding.navSecondButton.setClickableTextView(button)
        decorator.changeTitleAlignment()
    }

    fun setOnBackClickListener(onClickListener: OnClickListener) {
        binding.navBackButton.setOnClickListener(onClickListener)
    }

    fun setOnFirstOptionClickListener(onClickListener: OnClickListener) {
        binding.navFirstButton.setOnClickListener(onClickListener)
    }

    fun setOnSecondOptionClickListener(onClickListener: OnClickListener) {
        binding.navSecondButton.setOnClickListener(onClickListener)
    }

    private fun fillAttributes(attrs: AttributeSet) {
        attributes = context.obtainStyledAttributes(attrs, R.styleable.NavigationView)

        val data: NavigationData = attributes.run {
            NavigationData(
                toolbarTitle = getString(R.styleable.NavigationView_navigation_title)
            )
        }

        setUp(data)
        configureButtons(attributes)
        getNavigationStyleByAttributes()?.let { style -> decorator.setUpToolbar(style) }

        attributes.recycle()
    }

    private fun getNavigationStyleByAttributes(): NavigationStyleConfigData? {
        val style = attributes.getInt(
            R.styleable.NavigationView_navigation_style,
            NO_PREDEFINED_STYLE
        )

        return if (style != NO_PREDEFINED_STYLE)
            NavigationColor.getItemByOrdinal(style).style else null
    }

    private fun configureButtons(attributes: TypedArray) = with(binding) {
        getBackButtonAttribute(attributes)
        getFirstActionButton(attributes)
        getSecondActionButton(attributes)

    }

    private fun getSecondActionButton(attributes: TypedArray) {
        val icon = attributes.getResourceId(
            R.styleable.NavigationView_navigation_second_option_icon,
            NO_REFERENCE
        )
        val accessibility =
            attributes.getString(R.styleable.NavigationView_navigation_second_option_accessibility)

        if (icon != NO_REFERENCE) {
            val actionButton = NavigationButtonData(
                icon = icon,
                textAccessibility = accessibility,
            )
            setSecondActionButton(actionButton)
        }
    }

    private fun getFirstActionButton(attributes: TypedArray) {
        val icon = attributes.getResourceId(
            R.styleable.NavigationView_navigation_first_option_icon,
            NO_REFERENCE
        )
        val accessibility =
            attributes.getString(R.styleable.NavigationView_navigation_first_option_accessibility)

        if (icon != NO_REFERENCE) {
            val actionButton = NavigationButtonData(
                icon = icon,
                textAccessibility = accessibility,
            )
            setFirstActionButton(actionButton)
        }
    }

    private fun getBackButtonAttribute(attributes: TypedArray) {
        val hasBackButton = attributes.getBoolean(
            R.styleable.NavigationView_navigation_has_back_button,
            true
        )
        enableBackButton(hasBackButton)
    }

    private fun enableBackButton(hasButton: Boolean) = with(binding.navBackButton) {
        val backLabel = context.getString(R.string.button_back)
        this.visibility = INVISIBLE
        if (hasButton) {
            this.setAccessibleTextView(
                labelValue = context.getString(R.string.icon_arrow_mais),
                accessibilityValue = context.getString(R.string.button_accessibility, backLabel)
            )
        }
    }

    private fun TextView.setClickableTextView(button: NavigationButtonData) {
        this.apply {
            isVisible = true
            isClickable = true
            isFocusable = true
            button.icon?.let { value ->
                this.text = context.getString(value)
            }
            button.textAccessibility?.let { value ->
                setAccessibility(value)
            }
            setOnClickListener { button.action?.invoke() }
        }
    }

    companion object {
        private const val NO_REFERENCE = 98
        private const val NO_PREDEFINED_STYLE = 99
        private const val EXCEPTION = "This button should only be visible " +
                "when first button is visible on the layout"
    }
}