package com.example.myapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.customview.ClockView
import com.example.myapp.customview.MyEditText
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!
    private lateinit var vm: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        val format = SimpleDateFormat("HH : mm : ss")
        binding.clock.getCalender {
            val time = it.time
            binding.tvCurtime.text = format.format(time)
        }

        binding.btnSet.setOnClickListener {
            val hour = binding.edtHour.text.trim().toString()
            val minute = binding.edtMinute.text.trim().toString()
            val second = binding.edtSecond.text.trim().toString()
            if (vm.checkInput(hour, minute, second)) {
                binding.clock.setCalendar(hour.toInt(), minute.toInt(), second.toInt())
            }
        }
        vm.mess.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}