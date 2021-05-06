package com.budget.budget.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val backgroundWidth = 30f
    private val progressWidth = 30f

    private val backgroundPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = backgroundWidth
        isAntiAlias = true
    }

    private val progressPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = progressWidth
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 1f
        textSize = 100f
        isAntiAlias = true
    }

    var progress: Float = 0f
        set(value) {
           field = value
            invalidate()
        }

    private val oval = RectF()
    private var centreX = 0f
    private var centreY = 0f
    private var radius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        centreX = w.toFloat() / 2
        centreY = h.toFloat() / 2
        radius = w.toFloat() / 2 - progressWidth
        oval.set(
            centreX - radius,
            centreY - radius,
            centreX + radius,
            centreY + radius
        )
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(centreX, centreY, radius, backgroundPaint)
        canvas?.drawArc(oval, 270f, 360f * progress, false, progressPaint)
    }
}