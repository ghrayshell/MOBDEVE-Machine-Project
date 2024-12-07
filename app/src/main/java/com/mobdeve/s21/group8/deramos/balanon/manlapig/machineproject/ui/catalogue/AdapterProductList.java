package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R;
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.ProductManager;
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart.AdapterCartList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.MyViewHolder>{
    Context context;
    ArrayList<ProductModel> productModels;

    public AdapterProductList(Context context, ArrayList<ProductModel> productModels){
        this.context = context;
        this.productModels = productModels;
    }

    // REFERENCE to AdapterCartList
    private AdapterCartList cartAdapter;
    public void setCartAdapter(AdapterCartList cartAdapter) {
        this.cartAdapter = cartAdapter;
    }

    @NonNull
    @Override
    public AdapterProductList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, parent, false);
        return new AdapterProductList.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductList.MyViewHolder holder, int position) {
        ProductModel product = productModels.get(position);

        // Load image URL using Picasso (you can use Glide too)
        Picasso.get()
                .load(product.getImage())  // Assuming you have a URL or file name for the image
                .into(holder.ivImage);

        // Update product details in TextViews
        holder.tvName.setText(product.getName());        // Set product name
        holder.tvFabric.setText(product.getFabric());    // Set fabric information
        holder.tvColors.setText(product.getColors());    // Set colors information
        holder.tvPrice.setText(String.format("$%s", product.getPrice()));  // Set price

        // Handle the static resources based on product states
        if (product.isAdded()) {
            holder.ivPricetag.setImageResource(R.drawable.pricetag_blue);
            holder.ivAddBtn.setVisibility(View.GONE);
            holder.tvName.setTextColor(Color.WHITE);
            holder.tvFabric.setTextColor(Color.WHITE);
            holder.tvColors.setTextColor(Color.WHITE);
            holder.tvPrice.setTextColor(Color.WHITE);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#013B8A"));
        } else {
            holder.ivPricetag.setImageResource(R.drawable.pricetag_white);
            holder.ivAddBtn.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(Color.parseColor("#013B8A"));
            holder.tvFabric.setTextColor(Color.parseColor("#013B8A"));
            holder.tvColors.setTextColor(Color.parseColor("#013B8A"));
            holder.tvPrice.setTextColor(Color.parseColor("#013B8A"));
            holder.cardView.setCardBackgroundColor(Color.WHITE);
        }

        // Handle bookmark button logic
        if (product.isBookmarked()) {
            holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_checked);
        } else if (product.isAdded()) {
            holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_blue);
        } else {
            holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_white);
        }

        // Click listeners for Add and Bookmark buttons
        holder.ivAddBtn.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            product.setAdded(true);



            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the current user's ID

            // Use the productId to uniquely identify the product in the user's cart
            firestore.collection("users")
                    .document(userId)
                    .collection("cart")
                    .document(product.getProductId())  // Use the productId as the document ID in the user's cart
                    .set(product)  // Save product details in the user's cart
                    .addOnSuccessListener(aVoid -> {
                        // Successfully added product to cart
                        Toast.makeText(context, "Added to your cart!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Log.e("AdapterCartList", "Failed to add product to cart", e);
                    });

            ProductManager.getInstance().addToCart(product);
            // Add to cart adapter
            if (cartAdapter != null) {
                cartAdapter.notifyDataSetChanged();
            }

            notifyItemChanged(currentPosition);

        });

        holder.ivBookmarkBtn.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            product.setBookmarked(!product.isBookmarked());
            notifyItemChanged(currentPosition);
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage, ivPricetag, ivBookmarkBtn, ivAddBtn;
        TextView tvName, tvFabric, tvColors, tvPrice;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivProduct);
            ivPricetag = itemView.findViewById(R.id.pricetag);
            ivBookmarkBtn = itemView.findViewById(R.id.ivProductBookmark);
            ivAddBtn = itemView.findViewById(R.id.ivAddProduct);

            tvName = itemView.findViewById(R.id.tvProductName);
            tvFabric = itemView.findViewById(R.id.tvProductFabric);
            tvColors = itemView.findViewById(R.id.tvProductColors);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            cardView = itemView.findViewById(R.id.product_item);
        }
    }

    public void productAddedToCartChanged(ProductModel product, boolean addedToCart) {
        // Assuming `adapterProductList` is a reference to AdapterProductList in your activity or fragment
        for (int i = 0; i < getItemCount(); i++) {
            ProductModel productInList = productModels.get(i);
            if (productInList.getProductId().equals(product.getProductId())) {
                productInList.setAdded(addedToCart); // Set the "added" state back to false if removed
                notifyItemChanged(i); // Notify the adapter to update the UI
                break;
            }
        }
    }

}
