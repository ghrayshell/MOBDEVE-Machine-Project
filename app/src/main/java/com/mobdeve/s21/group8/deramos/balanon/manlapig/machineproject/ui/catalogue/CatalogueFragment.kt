package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.LandingActivity
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityCatalogueBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart.AdapterCartList
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart.MyCartViewModel

class CatalogueFragment : Fragment() {

    private var _binding: ActivityCatalogueBinding? = null
    private val binding get() = _binding!!

    private lateinit var catalogueViewModel: CatalogueViewModel
    private lateinit var adapterProductList: AdapterProductList
    private val productModels = ArrayList<ProductModel>()

    //REFERENCE
    private lateinit var adapterCartList: AdapterCartList


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        catalogueViewModel = ViewModelProvider(this).get(CatalogueViewModel::class.java)

        _binding = ActivityCatalogueBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvProductList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapterProductList = AdapterProductList(requireContext(), productModels)
        recyclerView.adapter = adapterProductList

        // Observe LiveData from ViewModel
        catalogueViewModel.productModels.observe(viewLifecycleOwner, { products ->
            // When data changes in ViewModel, update the adapter
            if (products != null) {
                productModels.clear()  // Clear current data in the list
                productModels.addAll(products)  // Add new data from Firestore
                adapterProductList.notifyDataSetChanged()  // Notify the adapter that the data has changed
            }
        })

        // Observe failure
        catalogueViewModel.errorMessage.observe(viewLifecycleOwner, { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        })

        /*// Set up Back Button
        binding.ivBackCatalogue.setOnClickListener {
            requireActivity().onBackPressed()
        }*/

        // Logout Button
        binding.ivLogoutCatalogue.setOnClickListener {
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
