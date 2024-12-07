package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.mycart;

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
import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterCartList extends RecyclerView.Adapter<AdapterCartList.MyViewHolder>{
    Context context;
    ArrayList<ProductModel> productModels;

    public AdapterCartList(Context context, ArrayList<ProductModel> productModels){
        this.context = context;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public AdapterCartList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, parent, false);
        return new AdapterCartList.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCartList.MyViewHolder holder, int position) {
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
            holder.ivDeleteBtn.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(Color.WHITE);
            holder.tvFabric.setTextColor(Color.WHITE);
            holder.tvColors.setTextColor(Color.WHITE);
            holder.tvPrice.setTextColor(Color.WHITE);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#013B8A"));
        } else {//THIS IS DELETED THO
            holder.ivPricetag.setImageResource(R.drawable.pricetag_white);
            holder.ivDeleteBtn.setVisibility(View.GONE);
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

        holder.ivDeleteBtn.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            product.setAdded(false);

            if (currentPosition != RecyclerView.NO_POSITION) {
                ProductModel productToDelete = productModels.get(currentPosition);

                // Remove from local list
                productModels.remove(currentPosition);
                notifyItemRemoved(currentPosition);

                // Remove from Firestore
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (userId != null) {
                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(userId)
                            .collection("cart")
                            .document(productToDelete.getProductId()) // Use productId as the document ID
                            .delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(context, "Item removed from cart", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Log.e("Firestore", "Error deleting item", e);
                                Toast.makeText(context, "Failed to remove item from cart", Toast.LENGTH_SHORT).show();
                            });
                }
            }
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

        ImageView ivImage, ivPricetag, ivBookmarkBtn, ivDeleteBtn;
        TextView tvName, tvFabric, tvColors, tvPrice;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivProduct);
            ivPricetag = itemView.findViewById(R.id.pricetag);
            ivBookmarkBtn = itemView.findViewById(R.id.ivProductBookmark);
            ivDeleteBtn = itemView.findViewById(R.id.ivDeleteProduct);

            tvName = itemView.findViewById(R.id.tvProductName);
            tvFabric = itemView.findViewById(R.id.tvProductFabric);
            tvColors = itemView.findViewById(R.id.tvProductColors);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            cardView = itemView.findViewById(R.id.product_item);
        }
    }

    public void updateData(List<ProductModel> newProductModels) {
        this.productModels.clear();
        this.productModels.addAll(newProductModels);
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
}
