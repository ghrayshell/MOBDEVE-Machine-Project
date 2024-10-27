package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue

import androidx.lifecycle.ViewModel
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.ProductModel

class MyCartViewModel : ViewModel() {

    val productModels = ArrayList<ProductModel>().apply {
        val productImages = intArrayOf(R.drawable.blinds, R.drawable.blinds, R.drawable.blinds)
        val productPricetag = intArrayOf(R.drawable.pricetag_white, R.drawable.pricetag_white, R.drawable.pricetag_white)
        val productBookmarkBtns = intArrayOf(R.drawable.bookmark_checked, R.drawable.bookmark_white, R.drawable.bookmark_blue)
        val productAddBtns = intArrayOf(R.drawable.add_product, R.drawable.add_product, R.drawable.add_product)
        val productNames = arrayOf("Combi Blinds", "Combi Blinds", "Combi Blinds")
        val productFabrics = arrayOf("Fabric Type", "Fabric Type", "Fabric Type")
        val productColors = arrayOf("Black, Red, White", "Black, Red, White", "Black, Red, White")
        val productPrices = arrayOf("₱190/sqm.", "₱190/sqm.", "₱190/sqm.")

        for (i in productNames.indices) {
            add(ProductModel(productImages[i], productPricetag[i], productBookmarkBtns[i], productAddBtns[i], productNames[i], productFabrics[i], productColors[i], productPrices[i]))
        }
    }
}