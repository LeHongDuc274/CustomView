package com.example.myapp.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PatternViewModel : ViewModel() {

    private var _mess = MutableLiveData<String>()
    val mess: LiveData<String> = _mess

    private var _pattern: MutableLiveData<List<Int>> = MutableLiveData()
    val pattern: LiveData<List<Int>> = _pattern
    fun setPattern(list: List<Int>) : Boolean {
        if (pattern.value.isNullOrEmpty()) {
            if (list.size >= 3) {
                _pattern.value = list
                _mess.value = "Lưu thành công"
                return false
            } else{
                _mess.value = "Lưu thất bại, mẫu hình phải lớn hơn 3 điểm"
                return true
            }
        } else {
            if(list == pattern.value){
                _mess.value = "Nhập chính xác"
                return false
            } else{
                _mess.value = "Nhập sai, hãy nhập lại"
                return true
            }
        }
    }
}