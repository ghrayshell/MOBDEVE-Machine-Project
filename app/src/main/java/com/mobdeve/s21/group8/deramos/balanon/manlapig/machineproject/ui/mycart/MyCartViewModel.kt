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
            val cartRef = db.collection("carts").document(currentUser.uid)

            // Fetch cart data
            cartRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Retrieve the products list from the document
                        val products = document.get("products") as? List<*>

                        // Check if products is not null and is a list of ProductModel
                        if (products != null) {
                            val productList = products.filterIsInstance<ProductModel>()
                            _productModels.value = productList
                        } else {
                            _errorMessage.value = "Cart is empty or invalid format."
                        }
                    } else {
                        _errorMessage.value = "No cart found for the user."
                    }
                }
                .addOnFailureListener { exception ->
                    _errorMessage.value = "Error fetching cart: ${exception.message}"
                }
        } else {
            _errorMessage.value = "User is not authenticated."
        }
    }
}