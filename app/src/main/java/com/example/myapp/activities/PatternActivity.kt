package com.example.myapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.myapp.R
import com.example.myapp.customview.MyEditText

class PatternActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern)
        var view = findViewById<MyEditText>(R.id.layout)
        view.setOnclickButton {
            var item = arrayOf("yellow", "purple_700", "black")
            var dialog = AlertDialog.Builder(this)
                .setTitle("Change Text Color")
                .setItems(item) { dialog, which ->
                    when (which) {
                        0 -> {
                            view.setTextColor(resources.getColor(R.color.yellow, null))
                        }
                        1 -> view.setTextColor(resources.getColor(R.color.purple_700, null))
                        2 -> view.setTextColor(resources.getColor(R.color.black, null))
                    }
                    dialog.dismiss()
                }.create().show()
        }
    }
}