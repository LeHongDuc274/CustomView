package com.example.myapp.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.transition.ArcMotion
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.example.myapp.R
import com.example.myapp.databinding.ActivityAnimationBinding

private var _binding: ActivityAnimationBinding? = null
private val binding get() = _binding!!
private val shortDuration = 300L
private val longDuration = 700L

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initControls()
    }

    private fun initViews() {
        binding.tvCrossFade.visibility = View.GONE
        binding.tv2.visibility = View.GONE

    }

    private fun initControls() {
        binding.btnCrossFade.setOnClickListener {
            crossfade()
        }
        binding.btnCircularReveal.setOnClickListener {
            circular()
        }
        binding.btnCircularReveal2.setOnClickListener {
            reveal()
        }
        binding.btnBlink.setOnClickListener {
            blink()
        }
        binding.btnZoomIn.setOnClickListener {
            zoomIn()
        }
        binding.btnZoomOut.setOnClickListener {
            zoomOut()
        }
        binding.btnMove.setOnClickListener {
            moveView()
        }
        binding.btnRotate.setOnClickListener {
            rotateView()
        }
        binding.btnNext.setOnClickListener {
            val intent = Intent(this,AnimationSecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun rotateView() {
        val ani = AnimationUtils.loadAnimation(applicationContext,R.anim.rotate)
        binding.tv8.startAnimation(ani)
    }

    private fun moveView() {
        val ani = AnimationUtils.loadAnimation(applicationContext,R.anim.move)
        binding.tv7.startAnimation(ani)
    }

    private fun zoomOut() {
        val ani = AnimationUtils.loadAnimation(applicationContext,R.anim.zoom_out)
        binding.tv6.startAnimation(ani)
    }

    private fun zoomIn() {
        val ani = AnimationUtils.loadAnimation(applicationContext,R.anim.zoom_in)
        binding.tv5.startAnimation(ani)
    }

    private fun blink() {
        val ani = AnimationUtils.loadAnimation(applicationContext,R.anim.blink)
        binding.tv4.startAnimation(ani)
    }


    private fun circular() {
        val cx = binding.tv2.width / 2
        val cy = binding.tv2.height / 2
        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(binding.tv2, cx, cy, 0f, finalRadius)
        binding.tv2.visibility = View.VISIBLE
        anim.start()
    }

    private fun reveal() {
        val cx = binding.tv3.width / 2
        val cy = binding.tv3.height / 2
        val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
        val anim = ViewAnimationUtils.createCircularReveal(binding.tv3, cx, cy, initialRadius, 0f)

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.tv3.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    private fun crossfade() {
        binding.tvCrossFade.visibility = View.GONE
        binding.tvCrossFade.apply {
            alpha = 0F
            visibility = View.VISIBLE
            animate().setDuration(longDuration)
                .setListener(null)
                .alpha(1F)
        }
    }
}