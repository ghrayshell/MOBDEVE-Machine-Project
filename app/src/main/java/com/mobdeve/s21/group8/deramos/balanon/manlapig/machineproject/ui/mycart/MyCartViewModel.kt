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
                .addOnSuccessListener { result ->
                    val products = ArrayList<ProductModel>()
                    for (document in result) {
                        val product = document.toObject(ProductModel::class.java)
                        product.productId = document.id  // Set the Firestore document ID in the ProductModel
                        products.add(product)
                        Log.d("MyCartFragment", "Product: ${product.name}, Fabric: ${product.fabric}\", Colors: ${product.colors}\", Price: ${product.price}\", ProductId: ${product.productId}")
                    }
                    _productModels.postValue(products)  // Update LiveData with the fetched products
                }
                .addOnFailureListener { exception ->
                    _errorMessage.value = "Error fetching cart: ${exception.message}"
                }
        }else {
            _errorMessage.value = "User is not authenticated."
        }
    }
}