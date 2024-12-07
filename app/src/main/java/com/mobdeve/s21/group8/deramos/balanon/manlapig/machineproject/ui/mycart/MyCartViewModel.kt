package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue.ProductModel

class MyCartViewModel : ViewModel() {
    private val _productModels = MutableLiveData<List<ProductModel>>()
    val productModels: LiveData<List<ProductModel>> get() = _productModels

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val db = FirebaseFirestore.getInstance()
    private val fAuth = FirebaseAuth.getInstance()

    init {
        fetchCartFromFirestore()
    }

    private fun fetchCartFromFirestore() {
        val currentUser = fAuth.currentUser
        if (currentUser != null) {
            // Get the cart collection for the current user
            val cartRef = db.collection("users").document(currentUser.uid).collection("cart")

            // Fetch all documents in the cart subcollection
            cartRef.get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val productList = mutableListOf<ProductModel>()

                        // Iterate through documents and map them to ProductModel
                        for (document in querySnapshot) {
                            val product = document.toObject(ProductModel::class.java)
                            productList.add(product)
                        }

                        _productModels.value = productList
                    } else {
                        _errorMessage.value = "Your cart is empty."
                        _productModels.value = emptyList() // Clear the product list if empty
                    }
                }
                .addOnFailureListener { exception ->
                    _errorMessage.value = "Error fetching cart: ${exception.message}"
                }
        }else {
            _errorMessage.value = "User is not authenticated."
        }
    }
}