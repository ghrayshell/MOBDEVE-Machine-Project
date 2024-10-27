package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.appointments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppointmentsViewModel : ViewModel() {

    private val _title = MutableLiveData<String>().apply {
        value = "Appointments"
    }
    val title: LiveData<String> = _title
}
