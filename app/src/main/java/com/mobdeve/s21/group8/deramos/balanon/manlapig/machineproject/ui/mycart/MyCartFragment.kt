package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart

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
import kotlin.collections.ArrayList
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.LandingActivity
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityMyCartBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue.AdapterProductList

class MyCartFragment : Fragment() {

    private var _binding: ActivityMyCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var myCartViewModel: MyCartViewModel
    private lateinit var adapterProductList: AdapterProductList


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize ViewModel
        myCartViewModel = ViewModelProvider(this).get(MyCartViewModel::class.java)

        // Inflate layout and get root view
        _binding = ActivityMyCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        val recyclerView = binding.rvProductList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with an empty list to start
        adapterProductList = AdapterProductList(requireContext(), ArrayList(emptyList()))
        recyclerView.adapter = adapterProductList

        myCartViewModel.productModels.observe(viewLifecycleOwner) { productList ->
            if (productList.isEmpty()) {
                binding.tvEmptyCart.visibility = View.VISIBLE
            } else {
                binding.tvEmptyCart.visibility = View.GONE
                adapterProductList.updateProductList(ArrayList(productList)) // Update adapter
            }
        }

        myCartViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        // Handle confirm button click (e.g., go to next screen)
        binding.ivConfirmServiceLocation.setOnClickListener {
            val intent = Intent(requireContext(), LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
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