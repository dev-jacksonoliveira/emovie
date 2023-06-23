package br.com.mfet.emovie.components

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.Interpolator
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.animation.PathInterpolatorCompat
import br.com.mfet.emovie.R

@Suppress("MagicNumber")
class ShimmerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val mUpdateListener = ValueAnimator.AnimatorUpdateListener { postInvalidate() }
    private val mShimmerPaint = Paint()
    private val mContentPaint = Paint()
    private val mDrawRect = RectF()
    private lateinit var colors: IntArray

    @ColorInt
    private var highlightColor: Int = ContextCompat.getColor(context, R.color.white)

    @ColorInt
    private var baseColor: Int = ContextCompat.getColor(context, R.color.gray_light)

    private var mValueAnimator: ValueAnimator? = null

    val isShimmerStarted: Boolean
        get() = mValueAnimator != null && mValueAnimator?.isStarted ?: false

    private val easeOutInterpolator: Interpolator
        get() = PathInterpolatorCompat.create(
            EASE_OUT_BEZIER[0], EASE_OUT_BEZIER[1],
            EASE_OUT_BEZIER[2], EASE_OUT_BEZIER[3]
        )

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.ShimmerLayout, 0, 0)
        setupGradientColors(array)

        setWillNotDraw(false)
        mShimmerPaint.isAntiAlias = true
        setLayerType(View.LAYER_TYPE_HARDWARE, mContentPaint)
        mShimmerPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        if (isInEditMode.not()) {
            updateShader()
            updateValueAnimator()
            postInvalidate()
        }
    }

    private fun setupGradientColors(array: TypedArray) {
        if (array.hasValue(R.styleable.ShimmerLayout_baseColor)) {
            setBaseColor(array.getColor(R.styleable.ShimmerLayout_baseColor, 0))
        }

        if (array.hasValue(R.styleable.ShimmerLayout_highlightColor)) {
            setHighlightColor(array.getColor(R.styleable.ShimmerLayout_highlightColor, 0))
        }

        colors = intArrayOf(baseColor, highlightColor, baseColor)
    }

    fun setHighlightColor(@ColorInt color: Int) {
        highlightColor = color
    }

    fun setBaseColor(@ColorInt color: Int) {
        baseColor = color
    }

    fun startShimmerAnimation() {
        if (mValueAnimator != null && !isShimmerStarted) {
            mValueAnimator?.start()
        }
    }

    fun stopShimmerAnimation() {
        if (mValueAnimator != null && isShimmerStarted) {
            mValueAnimator?.cancel()
        }
    }

    public override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isInEditMode.not()) {
            val width = width
            val height = height
            mDrawRect.set((2 * -width).toFloat(), (2 * -height).toFloat(), (4 * width).toFloat(), (4 * height).toFloat())
            updateShader()
        }
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isInEditMode.not()) startShimmerAnimation()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopShimmerAnimation()
    }

    public override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isInEditMode.not()) drawShimmer(canvas)
    }

    private fun drawShimmer(canvas: Canvas) {
        val width = width.toFloat()
        val animatedValue = mValueAnimator?.animatedFraction ?: 0f
        // Vertical coordinate
        val dx = offset(-width, width, animatedValue)
        // Horizontal coordinate
        val dy = 0f
        val saveCount = canvas.save()
        canvas.translate(dx, dy)
        canvas.drawRect(mDrawRect, mShimmerPaint)
        canvas.restoreToCount(saveCount)
    }

    private fun updateShader() {
        val viewWidth = width
        val viewHeight = height

        if (viewWidth != 0 && viewHeight != 0) {
            val shader = LinearGradient(0f, 0f, (viewWidth / 4).toFloat(), 0f, colors, COLOR_POSITIONS, Shader.TileMode.CLAMP)
            mShimmerPaint.shader = shader
        }
    }

    private fun updateValueAnimator() {
        var started = false
        mValueAnimator?.let {
            started = it.isStarted
            it.cancel()
            it.removeAllUpdateListeners()
        }

        mValueAnimator = ValueAnimator.ofFloat(0f, 1f + (REPEAT_DELAY / ANIMATION_DURATION).toFloat())
        mValueAnimator?.repeatMode = ValueAnimator.RESTART
        mValueAnimator?.repeatCount = ValueAnimator.INFINITE
        mValueAnimator?.duration = ANIMATION_DURATION + REPEAT_DELAY
        mValueAnimator?.addUpdateListener(mUpdateListener)
        mValueAnimator?.interpolator = easeOutInterpolator
        if (started) {
            mValueAnimator?.start()
        }
    }

    companion object {
        private val EASE_OUT_BEZIER = floatArrayOf(0f, 0f, 0.58f, 1f)
        private const val ANIMATION_DURATION = 1000L
        private const val REPEAT_DELAY = 100L
        private val COLOR_POSITIONS = floatArrayOf(0f, 0.5f, 1f)

        private fun offset(start: Float, end: Float, percent: Float) = start + (end - start) * percent
    }
}
