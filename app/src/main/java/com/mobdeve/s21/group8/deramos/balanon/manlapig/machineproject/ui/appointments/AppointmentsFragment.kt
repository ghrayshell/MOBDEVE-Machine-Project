package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityAppointmentsBinding

class AppointmentsFragment : Fragment() {

    private var _binding: ActivityAppointmentsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val appointmentsViewModel = ViewModelProvider(this).get(AppointmentsViewModel::class.java)

        _binding = ActivityAppointmentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val titleAppointments: TextView = binding.titleAppointments
        appointmentsViewModel.title.observe(viewLifecycleOwner) {
            titleAppointments.text = it
        }

        val ivLogo: ImageView = binding.ivLogo
        // Set any other properties or actions for your ImageView here

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
