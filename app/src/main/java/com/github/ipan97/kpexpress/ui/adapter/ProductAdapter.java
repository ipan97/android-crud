package com.github.ipan97.kpexpress.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.model.Product;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;
import com.github.ipan97.kpexpress.ui.activity.EditProductActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * @author Ipan Taupik Rahman.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;

    private OnClickItemCallback onClickItemCallback;

    private OnClickButtonDelete onClickButtonDelete;

    private Context mContext;

    public ProductAdapter(Context mContext, List<Product> products, OnClickItemCallback onClickItemCallback) {
        this.mContext = mContext;
        this.products = products;
        this.onClickItemCallback = onClickItemCallback;
    }

    public void setOnClickButtonDelete(OnClickButtonDelete onClickButtonDelete) {
        this.onClickButtonDelete = onClickButtonDelete;
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
                .load(RetrofitHttpClient.BASE_IMAGE_URL + product.getPhotoUrl())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.mIvPhoto);
        holder.mTvName.setText(product.getName());
        holder.mTvDescription.setText(product.getDescription());
        holder.itemView.setOnClickListener(v -> onClickItemCallback.onClick(products.get(holder.getAdapterPosition())));

        holder.mBtnEdit.setOnClickListener(v -> {
            Intent editProductActivity = new Intent(mContext, EditProductActivity.class);
            editProductActivity.putExtra("product.id", product.getId());
            editProductActivity.putExtra("product.name", product.getName());
            editProductActivity.putExtra("product.price", product.getPrice().toString());
            editProductActivity.putExtra("product.description", product.getDescription());
            editProductActivity.putExtra("product.photo", product.getPhotoUrl());
            mContext.startActivity(editProductActivity);
        });

        holder.mBtnDelete.setOnClickListener(v -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Delete Product");
            alertBuilder.setMessage("Are you sure delete ?");
            alertBuilder.setPositiveButton("Confirm", (dialog, which) -> onClickButtonDelete.onClick(product));
            alertBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;
        TextView mTvDescription;
        ImageView mIvPhoto;
        Button mBtnEdit;
        Button mBtnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvName = itemView.findViewById(R.id.tv_item_name);
            mTvDescription = itemView.findViewById(R.id.tv_item_description);
            mIvPhoto = itemView.findViewById(R.id.iv_item_photo);
            mBtnEdit = itemView.findViewById(R.id.btn_edit);
            mBtnDelete = itemView.findViewById(R.id.btn_delete);
        }


    }

    public interface OnClickItemCallback {
        void onClick(Product product);
    }

    public interface OnClickButtonDelete {
        void onClick(Product product);
    }
}
