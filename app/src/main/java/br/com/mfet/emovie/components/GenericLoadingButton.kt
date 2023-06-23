package br.com.mfet.emovie.components

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.FrameLayout

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import br.com.mfet.emovie.R
import com.airbnb.lottie.LottieAnimationView

class GenericLoadingButton<T : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: T? = null
    private var loadingButton: Button? = null
    private var loadingView : LottieAnimationView? = null
    //private val loadingButton: Button by bindView(R.id.generic_loading_button)
    //private val loadingView: LottieAnimationView by bindView(R.id.generic_loading_button_image)
    private lateinit var buttonText: CharSequence
    private var accessibilityText: String? = null

    fun initialize(binding: T) {
        this.binding = binding
        loadingButton = binding.root.findViewById(R.id.generic_loading_button)
        loadingView = binding.root.findViewById(R.id.generic_loading_button_image)
    }

    var clickAction: (() -> Unit)? = null
        set(value) {
            field = value
            loadingButton?.setOnClickListener { value?.invoke() }
        }

    init {
        View.inflate(context, R.layout.generic_loading_button_layout, this)
        attrs?.let { setupView(attrs) }
    }

    private fun setupView(attrs: AttributeSet) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GenericLoadingButton)

        val textColor = attributes.getColor(
            R.styleable.GenericLoadingButton_button_text_color,
            ContextCompat.getColor(context,R.color.yellow_primary)
        )

        buttonText = attributes.getText(
            R.styleable.GenericLoadingButton_button_text
        )

        val animation = attributes.getResourceId(
            R.styleable.GenericLoadingButton_button_animation_loading,
            R.raw.white_loading_button_animation
        )

        val backgroundResource =
            attributes.getDrawable(R.styleable.GenericLoadingButton_button_background_drawable)

        val fontSize = attributes.getDimensionPixelSize(
            R.styleable.GenericLoadingButton_button_text_size,
            NO_SIZE_PASSED
        )

        loadingButton?.apply {
            text = buttonText
            setTextColor(textColor)
            background = backgroundResource
        }

        if (!isInEditMode) {
            loadingView?.setAnimation(animation)
        }

        setupTextSize(fontSize)

        attributes.recycle()
    }

    private fun setupTextSize(textSize: Int) {
        if (textSize != NO_SIZE_PASSED) {
            loadingButton?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }
    }

    fun setAccessibilityText(contentDescription: String) {
        accessibilityText = contentDescription
        loadingButton?.contentDescription = accessibilityText
    }

    fun setText(text: String) {
        buttonText = text
        loadingButton?.text = buttonText
    }

    fun setTextColor(resourceColor: Int) {
        loadingButton?.setTextColor(ContextCompat.getColor(context, resourceColor))
    }

    fun setBackgroundButton(resourceBackground: Int) {
        loadingButton?.background = AppCompatResources.getDrawable(context, resourceBackground)
    }

    fun setButtonState(isButtonEnabled: Boolean) {
        loadingButton?.isEnabled = isButtonEnabled
    }

    fun getButtonText(): CharSequence? = loadingButton?.text

    fun setLoading(enable: Boolean) {
        if (enable) {
            loadingView?.isVisible = true
            loadingButton?.text = ""
            loadingButton?.isEnabled = false
            loadingButton?.contentDescription = resources.getString(R.string.loading)
        } else {
            loadingView?.isVisible = false
            loadingButton?.isEnabled = true
            loadingButton?.text = buttonText
            loadingButton?.contentDescription = if (accessibilityText.isNullOrEmpty()) {
                buttonText
            } else {
                accessibilityText
            }
        }
    }

    fun requestAccessibilityFocus() {
        loadingButton?.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
    }

    companion object {
        private const val NO_SIZE_PASSED = 0
    }
}