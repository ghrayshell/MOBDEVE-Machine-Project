package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityAppointmentsBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.AppointmentsAdapter
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.AppointmentsModel

class AppointmentsFragment : Fragment() {

    private var _binding: ActivityAppointmentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var appointmentsAdapter: AppointmentsAdapter
    private lateinit var appointmentsViewModel: AppointmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        appointmentsViewModel = ViewModelProvider(this).get(AppointmentsViewModel::class.java)

        _binding = ActivityAppointmentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        appointmentsAdapter = AppointmentsAdapter(appointmentsViewModel.getAppointments())
        binding.rvAppointmentsList.layoutManager = LinearLayoutManager(context)
        binding.rvAppointmentsList.adapter = appointmentsAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
