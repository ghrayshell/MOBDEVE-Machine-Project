package com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.ui.catalogue;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s21.group8.deramos.balanon.manlapig.machineproject.R;

import java.util.ArrayList;

public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.MyViewHolder>{
    Context context;
    ArrayList<ProductModel> productModels;

    public AdapterProductList(Context context, ArrayList<ProductModel> productModels){
        this.context = context;
        this.productModels = productModels;
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

        holder.ivImage.setImageResource(productModels.get(position).getImage());
        holder.ivPricetag.setImageResource(productModels.get(position).getPricetag());
        holder.ivBookmarkBtn.setImageResource(productModels.get(position).getBookmarkBtn());
        holder.ivAddBtn.setImageResource(productModels.get(position).getAddBtn());
        holder.tvName.setText(productModels.get(position).getName());
        holder.tvFabric.setText(productModels.get(position).getFabric());
        holder.tvColors.setText(productModels.get(position).getColors());
        holder.tvPrice.setText(productModels.get(position).getPrice());
        holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

        //if product is added
        if (product.getAdded()) {
            holder.ivPricetag.setImageResource(R.drawable.pricetag_blue);
            if(!product.getBookmarked()){
                holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_blue);
            }
            holder.ivAddBtn.setVisibility(View.GONE);
            holder.tvName.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tvFabric.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tvColors.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tvPrice.setTextColor(Color.parseColor("#FFFFFF"));
            holder.cardView.setCardBackgroundColor(Color.parseColor("#013B8A"));
        } else {
            holder.ivPricetag.setImageResource(product.getPricetag());
            holder.ivAddBtn.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(Color.parseColor("#013B8A"));
            holder.tvFabric.setTextColor(Color.parseColor("#013B8A"));
            holder.tvColors.setTextColor(Color.parseColor("#013B8A"));
            holder.tvPrice.setTextColor(Color.parseColor("#013B8A"));
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        //bookmark view
        if(product.getBookmarked())
            holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_checked);
        else if(product.getAdded())
            holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_blue);
        else
            holder.ivBookmarkBtn.setImageResource(R.drawable.bookmark_white);

        holder.ivAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentPosition = holder.getAdapterPosition();

                product.setAdded(true);
                notifyItemChanged(currentPosition);
            }
        });

        holder.ivBookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();

                if(product.getBookmarked())
                    product.setBookmarked(false);
                else
                    product.setBookmarked(true);

                notifyItemChanged(currentPosition);
            }
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
}
