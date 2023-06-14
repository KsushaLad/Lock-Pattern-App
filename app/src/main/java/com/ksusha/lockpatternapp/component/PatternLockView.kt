package com.ksusha.lockpatternapp.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.ksusha.lockpatternapp.R
import com.ksusha.lockpatternapp.patternmodel.Dot

class PatternLockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    var stageState: PatternViewStageState = PatternViewStageState.FIRST

    private var touchedPointX: Float = 0.0f
    private var touchedPointY: Float = 0.0f

    private var minCount: Int = 4
    private var maxCount: Int = 9

    private var markedDotList = mutableListOf<Dot>()
    private var initialDotList = mutableListOf<Dot>()
    private var state: PatternViewState = PatternViewState.Initial
    private var attrIsDotAnimate = true
    private var onChangeStateListener: ((state: PatternViewState) -> Unit)? = null
    private var countDownTimer: CountDownTimer? = null
    private var stagePasswords = linkedMapOf<PatternViewStageState, String>()

    @ColorInt private var attrDotColor = Color.DKGRAY
    @ColorInt private var attrLineColor = Color.DKGRAY

    @ColorInt private var attrErrorDotColor = Color.RED
    @ColorInt private var attrErrorLineColor = Color.RED

    @ColorInt private var attrSuccessDotColor = Color.BLUE
    @ColorInt private var attrSuccessLineColor = Color.BLUE

    private var rect = Rect()
    private val dotNumberKeyArray = arrayOf(arrayOf("1", "2", "3"), arrayOf("4", "5", "6"), arrayOf("7", "8", "9"))
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = 12f
        color = Color.DKGRAY
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PatternLockView,
            0,
            0
        ).apply {
            try {
                attrIsDotAnimate =
                    getBoolean(R.styleable.PatternLockView_patternLock_isAnimate, true)

                attrDotColor =
                    getColor(R.styleable.PatternLockView_patternLock_dotColor, Color.DKGRAY)
                attrLineColor =
                    getColor(R.styleable.PatternLockView_patternLock_lineColor, Color.DKGRAY)
                attrSuccessDotColor =
                    getColor(R.styleable.PatternLockView_patternLock_successDotColor, Color.BLUE)
                attrSuccessLineColor =
                    getColor(R.styleable.PatternLockView_patternLock_successLineColor, Color.BLUE)
                attrErrorDotColor =
                    getColor(R.styleable.PatternLockView_patternLock_errorDotColor, Color.RED)
                attrErrorLineColor =
                    getColor(R.styleable.PatternLockView_patternLock_errorLineColor, Color.RED)
            } finally {
                recycle()
            }
        }
        orientation = VERTICAL
        paint.color = attrErrorLineColor
        drawPatternView()
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        addInitialCanvas(canvas)
        if (state is PatternViewState.Error){
            updateViewState(state)
        }
        drawLine(canvas)
        if (state is PatternViewState.Error){
            countDownTimer = object: CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                   reset()
                }

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { motionEvent ->
            touchedPointX = motionEvent.x
            touchedPointY = motionEvent.y
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    if (state is PatternViewState.Success) {
                        return false
                    }
                    reset()
                    if (isTouchedDot(touchedPointX, touchedPointY)){
                        state = PatternViewState.Started
                        onChangeStateListener?.invoke(state)
                        invalidate()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (markedDotList.size != 0 && markedDotList.size >= minCount) {
                        PatternViewStageState.FIRST
                    }
                }
            }
        }
    }

    private fun isTouchedDot(touchedPointX: Float, touchedPointY: Float): Boolean {

    }

    private fun reset() {

    }

    private fun drawLine(canvas: Canvas?) {

    }

    private fun updateViewState(state: PatternViewState.Error) {

    }

    private fun addInitialCanvas(canvas: Canvas?) {

    }

    private fun drawPatternView() {

    }

}