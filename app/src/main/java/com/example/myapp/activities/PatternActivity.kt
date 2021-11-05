package com.example.myapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.MainActivity
import com.example.myapp.R
import com.example.myapp.customview.MyEditText
import com.example.myapp.databinding.ActivityPatternBinding
import com.example.myapp.viewmodel.PatternViewModel

class PatternActivity : AppCompatActivity() {
    private var _binding: ActivityPatternBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm : PatternViewModel
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        _binding = ActivityPatternBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this)[PatternViewModel::class.java]
        initControls()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initControls() {
        // editText
        binding.layout.setOnclickButton {
            var item = arrayOf("yellow", "purple_700", "black")
            var dialog = AlertDialog.Builder(this)
                .setTitle("Change Text Color")
                .setItems(item) { dialog, which ->
                    when (which) {
                        0 -> binding.layout.setTextColor(resources.getColor(R.color.yellow, null))
                        1 -> binding.layout.setTextColor(resources.getColor(R.color.purple_700, null))
                        2 -> binding.layout.setTextColor(resources.getColor(R.color.black, null))
                    }
                    dialog.dismiss()
                }.create().show()
        }
        //lock pattern
        if(vm.pattern.value.isNullOrEmpty()) showToast("Nhập mẫu hình cho lần đầu")
        binding.lockView.setOnlistenner {
            vm.setPattern(it)
        }
        vm.mess.observe(this,{
            showToast(it)
        })

        binding.btnNext.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(mess:String){
        Toast.makeText(this, mess, Toast.LENGTH_SHORT).show()
    }
}

