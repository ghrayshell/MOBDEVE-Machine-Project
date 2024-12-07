package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CatalogueViewModel : ViewModel() {

    private val _productModels = MutableLiveData<List<ProductModel>>()
    val productModels: LiveData<List<ProductModel>> get() = _productModels

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    init {
        fetchProductsFromFirestore()
    }

    private fun fetchProductsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val products = ArrayList<ProductModel>()
                for (document in result) {
                    val product = document.toObject(ProductModel::class.java)
                    products.add(product)
                    Log.d("CatalogueFragment", "Product: ${product.name}, Price: ${product.price}")
                }
                _productModels.postValue(products)  // Update LiveData with the fetched products
            }
            .addOnFailureListener { exception ->
                Log.e("CatalogueFragment", "Error getting products: $exception")
                _errorMessage.postValue("Error getting products: $exception")  // Set error message
            }
    }
}
