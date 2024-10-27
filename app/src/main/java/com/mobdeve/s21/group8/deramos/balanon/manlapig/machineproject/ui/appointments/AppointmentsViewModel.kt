package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.appointments

import androidx.lifecycle.ViewModel
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.AppointmentsModel

class AppointmentsViewModel : ViewModel() {

    private val appointmentsList = mutableListOf(
        AppointmentsModel("November 31, 2024", "De La Salle University", "Blinds Installation"),
        AppointmentsModel("December 31, 2024", "De La Salle University", "Blinds Installation"),
        AppointmentsModel("January 31, 2025", "De La Salle University", "Blinds Installation"),
        AppointmentsModel("February 31, 2025", "De La Salle University", "Blinds Installation")
    )

    fun getAppointments(): List<AppointmentsModel> = appointmentsList

    fun removeAppointment(position: Int) {
        if (position < appointmentsList.size) {
            appointmentsList.removeAt(position)
        }
    }
}
