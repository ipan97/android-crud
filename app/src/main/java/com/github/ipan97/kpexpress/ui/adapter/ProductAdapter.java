package com.github.ipan97.kpexpress.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.model.Product;

import java.util.List;

/**
 * @author Ipan Taupik Rahman.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;

    private OnClickItemCallback onClickItemCallback;


    public ProductAdapter(List<Product> products, OnClickItemCallback onClickItemCallback) {
        this.products = products;
        this.onClickItemCallback = onClickItemCallback;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        Glide.with(holder.itemView.getContext())
                .load(product.getPhotoUrl())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.mIvPhoto);
        holder.mTvTitle.setText(product.getName());
        holder.mTvDescription.setText(product.getDescription());
        holder.itemView.setOnClickListener(v -> onClickItemCallback.onClick(products.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;
        TextView mTvDescription;
        ImageView mIvPhoto;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.tv_item_title);
            mTvDescription = itemView.findViewById(R.id.tv_item_description);
            mIvPhoto = itemView.findViewById(R.id.iv_item_photo);
        }


    }

    public interface OnClickItemCallback {
        void onClick(Product product);
    }
}
