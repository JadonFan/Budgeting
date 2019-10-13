package com.example.budgetapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircularProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) ***REMOVED***
    private val backgroundWidth: Float = 30f
    private val progressWidth: Float = 30f

    private val backgroundPaint = Paint().apply ***REMOVED***
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = backgroundWidth
        isAntiAlias = true
***REMOVED***

    private val progressPaint = Paint().apply ***REMOVED***
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = progressWidth
        isAntiAlias = true
***REMOVED***

    private val textPaint = Paint().apply ***REMOVED***
        color = Color.BLACK
        strokeWidth = 1f
        textSize = 100f
        isAntiAlias = true
***REMOVED***

    var progress: Float = 0f
        set(value) ***REMOVED***
           field = value
            invalidate()
***REMOVED***

    private val oval = RectF()
    private var centreX: Float = 0f
    private var centreY: Float = 0f
    private var radius: Float = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) ***REMOVED***
        centreX = w.toFloat() / 2
        centreY = h.toFloat() / 2
        radius = w.toFloat() / 2 - progressWidth
        oval.set(centreX - radius, centreY - radius, centreX + radius,
            centreY + radius)
        super.onSizeChanged(w, h, oldw, oldh)
***REMOVED***

    override fun onDraw(canvas: Canvas?) ***REMOVED***
        super.onDraw(canvas)
        canvas?.drawCircle(centreX, centreY, radius, backgroundPaint)
        canvas?.drawArc(oval, 270f, 360f * progress, false, progressPaint)
***REMOVED***
***REMOVED***