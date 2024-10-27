package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is appointments Fragment"
    }
    val text: LiveData<String> = _text
}