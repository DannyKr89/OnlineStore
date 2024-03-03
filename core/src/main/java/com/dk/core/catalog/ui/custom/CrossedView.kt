package com.dk.core.catalog.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet

class CrossedView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val paint = Paint()

    init {
        paint.color = currentTextColor
        paint.strokeWidth = 2f
        paint.isAntiAlias = true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startX = 0f
        val startY = (height * 2 / 3).toFloat()
        val stopX = width.toFloat()
        val stopY = (height / 3).toFloat()

        canvas.drawLine(startX, startY, stopX, stopY, paint)
    }

}