package com.example.myapp.customview

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.minus
import androidx.core.graphics.plus
import com.example.myapp.R
import kotlinx.coroutines.*
import java.lang.Math.sin
import java.util.*
import kotlin.math.PI
import kotlin.math.cos

class ClockView(context: Context, attrs: AttributeSet?) : View(context, attrs) {


    private var mHeight = 0
    private var mWidth = 0
    private var mRadius = 0F
    private var mMinutehand = 0F
    private var mSeconhand = 0F
    private var mHourhand = 0F
    private val listHours = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    private val mPadding = 50F
    private var calendar: Calendar = Calendar.getInstance(Locale.KOREA)
    private var curHour = calendar[Calendar.HOUR_OF_DAY]
    private var isChangedtime = false
    var change : ((Calendar) -> Unit)? = null
    val mPaintCir: Paint = Paint().apply {
        setColor(Color.WHITE)
        style = Paint.Style.STROKE
        strokeWidth = 5F
        isAntiAlias = true
    }
    val mPaintDot = Paint().apply {
        setColor(Color.WHITE)
        style = Paint.Style.FILL_AND_STROKE
    }
    val mPaintLineHour = Paint().apply {
        setColor(Color.WHITE)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 15F
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND

    }
    val mPaintLineMinute = Paint().apply {
        setColor(Color.WHITE)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 10F
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }
    val mPaintLineSecond = Paint().apply {
        setColor(Color.WHITE)
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 5F
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }
    val mReact = Rect()

    val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18F, resources.displayMetrics)
    val largSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 22F, resources.displayMetrics)
    var mPaintText = Paint().apply {
        setColor(Color.WHITE)
        style = Paint.Style.FILL
        textSize = size
    }
    var mPaintHighLight = Paint().apply {
        setColor(Color.RED)
        style = Paint.Style.FILL
        textSize = largSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        val size = Math.min(mWidth, mHeight)
        setMeasuredDimension(size, size)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mRadius = Math.min(w / 2F, h / 2F) - mPadding // trừ đi khoảng text bên ngoài
        mSeconhand = mRadius * 0.8F
        mMinutehand = mRadius * 0.65F
        mHourhand = mRadius * 0.5F
    }

    //
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.DKGRAY) // chú ý thứ tự trước sau
        canvas?.drawCircle(mWidth / 2F, mHeight / 2F, mRadius, mPaintCir)
        drawNumber(canvas)
        drawCenterDot(canvas)
        drawClockhand(canvas)

    }
     var job: Job? = null
    fun setCalendar(hour: Int, minute: Int, second: Int) {
        job?.let {
            it.cancel()
            isChangedtime = false
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        isChangedtime = true
        job = CoroutineScope(Dispatchers.Default).launch {
           while (isChangedtime) {
                delay(1000)
               calendar.add(Calendar.SECOND, 1)
            }
        }
    }
    fun getCalender(action:((Calendar) -> Unit)?){
        change = action
    }
    private fun drawClockhand(canvas: Canvas?) {
        if (!isChangedtime) {
            calendar = Calendar.getInstance(Locale.KOREA)
        }
        change?.invoke(calendar)
        //check cur change
        if(curHour!=calendar[Calendar.HOUR_OF_DAY]){
            curHour = calendar[Calendar.HOUR_OF_DAY]
            drawNumber(canvas)
        }
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        hour = if (hour > 12) (hour - 12) else hour
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        drawhand((hour + minute / 60F) * 5, canvas, mHourhand, mPaintLineHour)
        drawhand(minute.toFloat(), canvas, mMinutehand, mPaintLineMinute)
        drawhand(second.toFloat(), canvas, mSeconhand, mPaintLineSecond)
        postInvalidateDelayed(1000)
        invalidate()
    }

    private fun drawhand(time: Float, canvas: Canvas?, radius: Float, paint: Paint) {
        val angle = 2 * PI * time / 60F - PI / 2
        canvas?.drawLine(
            mWidth / 2F, mHeight / 2F,
            (mWidth / 2 + cos(angle) * radius).toFloat(),
            (mWidth / 2 + sin(angle) * radius).toFloat(),
            paint
        )
    }

    private fun drawCenterDot(canvas: Canvas?) {
        canvas?.drawCircle(mWidth / 2F, mHeight / 2F, 10F, mPaintDot)
    }

    private fun drawNumber(canvas: Canvas?) {
        listHours.forEach { hour ->
            mPaintText.getTextBounds(hour.toString(), 0, hour.toString().length, mReact)
            mReact.minus(100)
            val angle = PI / 6 * (hour - 3)
            val xDot = mWidth / 2 + cos(angle) * (mRadius)
            val yDot = mHeight / 2 + sin(angle) * (mRadius)
            val mX = (mWidth / 2 + cos(angle) * (mRadius - mPadding)).toFloat()
            val mY =
                (mHeight / 2 + sin(angle) * (mRadius - mPadding) ).toFloat()
            canvas?.save()
            canvas?.rotate (hour*30F,mX,mY)
            if (!compareHour(hour,curHour)){
                canvas?.drawText(hour.toString(), mX - mReact.width()/2, mY +mReact.height() / 2, mPaintText)
            } else{
                canvas?.drawText(hour.toString(), mX - mReact.width()/2, mY +mReact.height() / 2, mPaintHighLight)
            }
            canvas?.restore()
            canvas?.save()
            canvas?.drawCircle(xDot.toFloat(), yDot.toFloat(), 8F, mPaintDot)
        }
    }
    private fun compareHour(hour12: Int,hour24: Int) : Boolean{
        return (hour12==hour24) || (hour12 == (hour24 -12))
    }
    override fun onDetachedFromWindow() {
        isChangedtime = false
        super.onDetachedFromWindow()
    }
}