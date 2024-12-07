package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue.ProductModel;

import java.util.ArrayList;

public class ProductManager {
    private static final ProductManager instance = new ProductManager();

    private ArrayList<ProductModel> productList;
    private ArrayList<ProductModel> cartList;

    private ProductManager() {
        productList = new ArrayList<>();
        cartList = new ArrayList<>();
    }

    public static ProductManager getInstance() {
        return instance;
    }

    public ArrayList<ProductModel> getProductList() {
        return productList;
    }

    public ArrayList<ProductModel> getCartList() {
        return cartList;
    }

    public void addToCart(ProductModel product) {
        if (!cartList.contains(product)) {
            cartList.add(product);
            product.setAdded(true);
        }
    }

    public void removeFromCart(ProductModel product) {
        cartList.remove(product);
        product.setAdded(false);
    }
}