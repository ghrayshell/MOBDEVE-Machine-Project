package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.databinding.ActivityMyCartBinding
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.AdapterProductList

class MyCartFragment : Fragment() {

    private var _binding: ActivityMyCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myCartViewModel = ViewModelProvider(this).get(MyCartViewModel::class.java)

        _binding = ActivityMyCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvProductList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapterProductList = AdapterProductList(requireContext(), myCartViewModel.productModels)
        recyclerView.adapter = adapterProductList

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}