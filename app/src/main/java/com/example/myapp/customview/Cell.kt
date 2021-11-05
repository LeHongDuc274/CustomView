package com.example.myapp.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import com.example.myapp.STATE_ERROR
import com.example.myapp.STATE_NORMAL
import com.example.myapp.STATE_SELECTED

internal class Cell(val pos: Int, context: Context?) : View(context) {
    private var state = STATE_NORMAL
    private var paint = Paint().apply {
        isAntiAlias = true
    }
    private val backgroundColor: Int = Color.CYAN
    private val normalColor: Int = Color.WHITE
    private val selectedColor: Int = Color.BLUE
    private val errorColor: Int = Color.RED
    private var radius: Float = 0F
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth = MeasureSpec.getSize(widthMeasureSpec) / 3
        val mHeight = MeasureSpec.getSize(heightMeasureSpec) / 3
        val size = Math.min(mHeight,mWidth)
        radius = size / 10F
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        when (state) {
            STATE_NORMAL -> {
                drawView(canvas, normalColor)
            }
            STATE_SELECTED -> {
                drawView(canvas, selectedColor)
            }
            STATE_ERROR -> {
                drawView(canvas, errorColor)
            }
        }


    }

    private fun drawView(canvas: Canvas?, color: Int) {
        //draw backGround
        var centerX = width / 2
        var centerY = height / 2
        Log.e("cell", "${centerX} ${centerY}")
        canvas?.drawColor(backgroundColor)
        //draw circle
        paint.color = color
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius, paint)
    }



    fun setState(newState: Int) {
        state = newState
    }

    fun getCenter(): Pair<Int, Int> {
        var x = left + (right - left) / 2
        var y = top + (bottom - top) / 2
        return Pair(x,y)
    }
}