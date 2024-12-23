package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.appointments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.LandingActivity
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityAppointmentsBinding

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

        appointmentsAdapter =
            AppointmentsAdapter(
                appointmentsViewModel.getAppointments()
            )
        binding.rvAppointmentsList.layoutManager = LinearLayoutManager(context)
        binding.rvAppointmentsList.adapter = appointmentsAdapter

        /*// Set up Back Button
        binding.ivBackAppointments.setOnClickListener {
            requireActivity().onBackPressed()
        }*/

        // Logout Button
        binding.ivLogoutAppointments.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(requireContext(), LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
            Toast.makeText(requireContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
