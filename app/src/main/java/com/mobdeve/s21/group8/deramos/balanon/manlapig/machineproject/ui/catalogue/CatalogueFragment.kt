package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityCatalogueBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.AdapterProductList

class CatalogueFragment : Fragment() {

    private var _binding: ActivityCatalogueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val catalogueViewModel = ViewModelProvider(this).get(CatalogueViewModel::class.java)

        _binding = ActivityCatalogueBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvProductList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapterProductList = AdapterProductList(requireContext(), catalogueViewModel.productModels)
        recyclerView.adapter = adapterProductList

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}