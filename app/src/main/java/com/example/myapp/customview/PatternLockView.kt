package com.example.myapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout
import androidx.core.view.size
import com.example.myapp.STATE_ERROR
import com.example.myapp.STATE_NORMAL
import com.example.myapp.STATE_SELECTED

class PatternLockView : GridLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initCells()
        columnCount = nRow
        rowCount = nCol
    }

    private var listCell = mutableListOf<Cell>()
    private var listCellSelected = mutableListOf<Cell>()
    private var nRow = 3
    private var nCol = 3
    private var curX = 0
    private var curY = 0
    private val hitBoxPercent = 3/5
    private var path = Path()
    private var callback: ((List<Int>) -> Boolean)? = null
    private var paintLine = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.WHITE
        strokeWidth = 10F
    }
    private fun initCells() {
        for (i in 0..2) {
            for (j in 0..2) {
                val pos = i * 3 + j
                val cell = Cell(pos, context)
                addView(cell)
                listCell.add(cell)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                reset()
                val cell = selectCell(event.x.toInt(), event.y.toInt())
                if (cell == null) return false
                else {
                    onCellSelected(cell)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val cell = selectCell(event.x.toInt(), event.y.toInt())
                if (cell != null) {
                    if (!listCellSelected.contains(cell)) onCellSelected(cell)
                } else {
                    curX = event.x.toInt()
                    curY = event.y.toInt()
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> finishMove()
            MotionEvent.ACTION_CANCEL -> reset()
            else -> return false
        }
        return true
    }

    private fun reset() {
        curX = 0
        curY = 0
        listCellSelected.forEach {
            it.setState(STATE_NORMAL)
            it.invalidate()
        }
        listCellSelected.clear()
        path.reset()
        invalidate()
    }

    private fun finishMove() {
        curX = 0
        curY = 0
        val listNumber = listCellSelected.map {
            it.pos
        }
        if (listNumber.isNotEmpty()) {
            val isError = callback?.invoke(listNumber)
            isError?.let {
                if (isError) {
                    setOnError()
                } else {
                    postDelayed({ reset() }, 1000)
                }
            }
        }

        invalidate()
    }

    private fun setOnError() {
        listCellSelected.forEach {
            it.setState(STATE_ERROR)
            it.invalidate()
        }
        postDelayed({ reset() }, 1000)
    }

    fun setOnlistenner(action: (List<Int>) -> Boolean) {
        callback = action
    }

    private fun onCellSelected(cell: Cell) {
        listCellSelected.add(cell)
        Log.e("size", listCellSelected.size.toString())

        cell.setState(STATE_SELECTED)
        cell.invalidate()

        var center = cell.getCenter()
        if (listCellSelected.size == 1) {
            path.moveTo(center.first.toFloat(), center.second.toFloat())
        } else {
            path.lineTo(center.first.toFloat(), center.second.toFloat())
        }
        // invalidate()
    }

    private fun selectCell(mX: Int, mY: Int): Cell? {
        listCell.forEach {
            if (checkCellIsSelected(it, mX, mY)) return it
        }
        return null
    }

    private fun checkCellIsSelected(view: View, mX: Int, mY: Int): Boolean {
        val hitBox = view.height * 2/5
        return (mX in (view.left + hitBox)..(view.right - hitBox) && mY in (view.top + hitBox)..(view.bottom - hitBox))
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawPath(path, paintLine)
        if (curX > 0 && curY > 0 && listCellSelected.isNotEmpty()) {
            var center = listCellSelected[listCellSelected.size - 1].getCenter()
            canvas?.drawLine(
                center.first.toFloat(),
                center.second.toFloat(),
                curX.toFloat(),
                curY.toFloat(),
                paintLine
            )
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}