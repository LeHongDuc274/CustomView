package com.example.myapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.myapp.R

@RequiresApi(Build.VERSION_CODES.M)
class MyEditText( context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    lateinit var editText: EditText
    lateinit var btn: ImageButton
    var color: Int
    var action:((ImageButton)->Unit)? = null
    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.EditTextButton,
            0, 0
        ).apply {
            try {
                color = getColor(R.styleable.EditTextButton_mycolor, resources.getColor(R.color.black,null))
            } finally {
                recycle()
            }
        }
        LayoutInflater.from(context).inflate(R.layout.edittext_button, this)
        editText = this.findViewById(R.id.edt)
        editText.setTextColor(color)
        btn = this.findViewById(R.id.btn)
        btn.setOnClickListener {
            action?.invoke(it as ImageButton)
        }
    }

    fun setOnclickButton(ac:(ImageButton)->Unit){
        action = ac
    }
    fun setTextColor(value: Int) {
        editText.setTextColor(value)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}