package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    ArrayList<ProductModel> productModels = new ArrayList<>();
    private ImageView btnBackMyCart;
    private ImageView ivLogo;
    private TextView titleMyCart;

    //private Switch switchOCV, switchBI;
    //private ImageView ivConfirmServices;

    //hardcoded for now
    int[] productImages = {R.drawable.blinds, R.drawable.blinds,
            R.drawable.blinds};

    int[] productPricetag = {R.drawable.pricetag_white, R.drawable.pricetag_white,
            R.drawable.pricetag_white};

    int[] productBookmarkBtns = {R.drawable.bookmark_checked, R.drawable.bookmark_white,
            R.drawable.bookmark_blue};

    int[] productAddBtns = {R.drawable.add_product, R.drawable.add_product,
            R.drawable.add_product};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        this.btnBackMyCart = findViewById(R.id.ivBackMyCart);
        this.ivLogo = findViewById(R.id.ivLogo);
        this.titleMyCart = findViewById(R.id.titleMyCart);

        RecyclerView rvMyCart = findViewById(R.id.rvProductList);

        setupProductModels();

        AdapterProductList adapterProductList = new AdapterProductList(this, productModels);
        rvMyCart.setAdapter(adapterProductList);
        rvMyCart.setLayoutManager(new LinearLayoutManager(this));

        btnBackMyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //hardcoded for now
    private void setupProductModels(){
        String[] productNames = {"Combi Blinds", "Combi Blinds", "Combi Blinds"};

        String[] productFabrics = {"Fabric Type", "Fabric Type", "Fabric Type"};

        String[] productColors = {"Black, Red, White", "Black, Red, White", "Black, Red, White"};

        String[] productPrices = {"₱190/sqm.","₱190/sqm.","₱190/sqm."};

        for(int i=0; i<productNames.length; i++){
            productModels.add(new ProductModel(productImages[i], productPricetag[i], productBookmarkBtns[i],
                    productAddBtns[i],  productNames[i], productFabrics[i],
                    productColors[i], productPrices[i]));
        }
    }
}