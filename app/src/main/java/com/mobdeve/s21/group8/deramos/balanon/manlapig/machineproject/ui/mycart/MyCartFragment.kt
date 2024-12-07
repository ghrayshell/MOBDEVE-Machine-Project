package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlin.collections.ArrayList
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.LandingActivity
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityMyCartBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue.ProductModel

class MyCartFragment : Fragment() {

    private var _binding: ActivityMyCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var myCartViewModel: MyCartViewModel
    private lateinit var adapterCartList: AdapterCartList
    private val productModels = ArrayList<ProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myCartViewModel = ViewModelProvider(this).get(MyCartViewModel::class.java)

        _binding = ActivityMyCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvProductList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapterCartList = AdapterCartList(requireContext(), ArrayList(emptyList()))
        recyclerView.adapter = adapterCartList


        // Observe LiveData from ViewModel
        myCartViewModel.productModels.observe(viewLifecycleOwner, { products ->
            if (products != null) {
                adapterCartList.updateData(products) // Update the adapter's dataset
            }
        })

        // Observe error messages from ViewModel
        myCartViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        // Handle confirm button click (e.g., go to next screen)
        binding.ivConfirmServiceLocation.setOnClickListener {
            val i = Intent(requireContext(), MapsActivity::class.java)
            startActivity(i)
        }


        // Set up Back Button and sign out user
        binding.ivBackMyCart.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}