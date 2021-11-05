package com.example.myapp.activities

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.myapp.R
import com.example.myapp.databinding.ActivityAnimationSecondBinding

class AnimationSecondActivity : AppCompatActivity() {
    private var _binding : ActivityAnimationSecondBinding? = null
    val binding get() = _binding!!
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ib1.setOnClickListener {
            val path = Path().apply {
                arcTo(0F, 0F, binding.ib1.pivotX + 300, binding.ib1.pivotY +300, 270f, -180f, true)
            }
            val animator = ObjectAnimator.ofFloat(it, View.X, View.Y, path).apply {
                duration = 1200
                start()
            }
            val view : View  = TextView(this).apply {
                text = "text ${count++}"
                id = R.id.text
                textSize = 30F
                setTextColor(Color.BLUE)
                minHeight = 50
            }
            binding.layout2.addView(view,0)
        }
    }
}