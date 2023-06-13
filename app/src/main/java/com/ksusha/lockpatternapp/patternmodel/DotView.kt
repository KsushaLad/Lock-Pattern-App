package com.ksusha.lockpatternapp.patternmodel

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.OVAL
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import com.ksusha.lockpatternapp.R
import com.ksusha.lockpatternapp.utils.ANIMATION_DURATION
import com.ksusha.lockpatternapp.utils.ANIMATION_SCALE_VALUE

class DotView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): AppCompatImageView(context, attrs, defStyleAttr) {

    private var animator: ObjectAnimator? = null
    var key: String? = null
        private set
    init {
        setImageDrawable(GradientDrawable().apply {
            layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.dot_view_size),
                resources.getDimensionPixelSize(R.dimen.dot_view_size)
            )
            shape = OVAL
            setColor(Color.DKGRAY)
        })
        setWillNotDraw(false)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
        animator?.removeAllListeners()
        animator = null
    }

    fun setKey(key: String) {
        this.key = key
    }

    fun setDotViewColor(@ColorInt color: Int) {
        (drawable as GradientDrawable).setColor(color)
    }

    fun animateDotView() {
        animator = ObjectAnimator.ofPropertyValuesHolder(
            this, PropertyValuesHolder.ofFloat("ScaleX", ANIMATION_SCALE_VALUE),
            PropertyValuesHolder.ofFloat("scaleY", ANIMATION_SCALE_VALUE)
        ).apply {
            duration = ANIMATION_DURATION
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.RESTART
            start()
        }
        animator?.doOnEnd {
            animator?.removeAllListeners()
            animator = null
        }
    }

}