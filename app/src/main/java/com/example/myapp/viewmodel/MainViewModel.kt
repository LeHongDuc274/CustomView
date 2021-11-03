package com.example.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var _mess  = MutableLiveData<String>()
    val mess : LiveData<String> = _mess

    fun checkInput(hour: String, minute: String, second: String): Boolean {
        if (hour.isNotEmpty() && minute.isNotEmpty() && second.isNotEmpty()) {
            return (hour.toInt() in 1..12 && minute.toInt() in 1..60 && second.toInt() in 1..60)
        } else {
            _mess.value = "Hãy nhập đủ thông tin"
            return false
        }
    }
}