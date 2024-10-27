package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R;

import java.util.ArrayList;

public class CatalogueActivity extends AppCompatActivity {

    ArrayList<ProductModel> productModels = new ArrayList<>();
    private ImageView btnBackCatalogue;
    private ImageView ivLogo;
    private TextView titleCatalogue;
    private ImageView ivCart;
    private TextView titleCartItems;
    private TextView tvAmtCartItems;

    //hardcoded for now
    int[] productImages = {R.drawable.blinds, R.drawable.blinds,
                           R.drawable.blinds, R.drawable.blinds, R.drawable.blinds};

    int[] productPricetag = {R.drawable.pricetag_white, R.drawable.pricetag_white,
                             R.drawable.pricetag_white, R.drawable.pricetag_white, R.drawable.pricetag_white};

    int[] productBookmarkBtns = {R.drawable.bookmark_checked, R.drawable.bookmark_white,
            R.drawable.bookmark_blue, R.drawable.bookmark_checked, R.drawable.bookmark_white};

    int[] productAddBtns = {R.drawable.add_product, R.drawable.add_product,
            R.drawable.add_product, R.drawable.add_product, R.drawable.add_product};

    /*Spinner spinner = findViewById(R.id.spinner);
    FrameLayout parentLayout = findViewById(R.id.frameSpinner); // The layout wrapping the spinner

    parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            // Remove the listener to prevent repeated calls
            parentLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            // Create a larger touchable area
            Rect delegateArea = new Rect();
            spinner.getHitRect(delegateArea);

            // Increase the hit area by expanding the rectangle
            int padding = 50; // This expands the touch area by 50px on each side
            delegateArea.top -= padding;
            delegateArea.bottom += padding;
            delegateArea.left -= padding;
            delegateArea.right += padding;

            // Set the touch delegate
            parentLayout.setTouchDelegate(new TouchDelegate(delegateArea, spinner));
        }
    });*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        //this.btnBackCatalogue = findViewById(R.id.ivBackCatalogue);
        this.ivLogo = findViewById(R.id.ivLogo);
        this.titleCatalogue = findViewById(R.id.titleCatalogue);
        this.ivCart = findViewById(R.id.ivCart);
        this.titleCartItems = findViewById(R.id.titleCartItems);
        this.tvAmtCartItems = findViewById(R.id.tvAmtCartItems);

        RecyclerView rvCatalogue = findViewById(R.id.rvProductList);

        setupProductModels();

        AdapterProductList adapterProductList = new AdapterProductList(this, productModels);
        rvCatalogue.setAdapter(adapterProductList);
        rvCatalogue.setLayoutManager(new LinearLayoutManager(this));

        btnBackCatalogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //hardcoded for now
    private void setupProductModels(){
        String[] productNames = {"Combi Blinds", "Combi Blinds", "Combi Blinds",
                                 "Combi Blinds", "Combi Blinds"};

        String[] productFabrics = {"Fabric Type", "Fabric Type", "Fabric Type",
                                   "Fabric Type", "Fabric Type"};

        String[] productColors = {"Black, Red, White", "Black, Red, White", "Black, Red, White",
                                  "Black, Red, White", "Black, Red, White"};

        String[] productPrices = {"₱190/sqm.","₱190/sqm.","₱190/sqm.","₱190/sqm.", "₱190/sqm."};

        for(int i=0; i<productNames.length; i++){
            productModels.add(new ProductModel(productImages[i], productPricetag[i], productBookmarkBtns[i],
                                              productAddBtns[i],  productNames[i], productFabrics[i],
                                              productColors[i], productPrices[i]));
        }
    }

}