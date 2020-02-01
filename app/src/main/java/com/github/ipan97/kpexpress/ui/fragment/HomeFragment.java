package com.github.ipan97.kpexpress.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.model.Product;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;
import com.github.ipan97.kpexpress.ui.activity.AddProductActivity;
import com.github.ipan97.kpexpress.ui.activity.ProductDetailActivity;
import com.github.ipan97.kpexpress.ui.adapter.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_product)
    RecyclerView mRvProduct;

    @BindView(R.id.fab_add_product)
    FloatingActionButton mFabAddProduct;

    @BindView(R.id.srl_home_layout)
    SwipeRefreshLayout mSrlHomeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        showProducts();

        mSrlHomeLayout.setOnRefreshListener(this::showProducts);

        mFabAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddProductActivity.class));
        });
        return view;
    }


    private void showProducts() {
        mRvProduct.setLayoutManager(new LinearLayoutManager(getActivity()));

        RetrofitHttpClient.client().getProducts()
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getData());
                                List<Product> products = gson.fromJson(json, new TypeToken<List<Product>>() {
                                }.getType());

                                ProductAdapter productAdapter = new ProductAdapter(getActivity(), products, product -> {
                                    showProductDetailActivity(product);
                                });
                                mRvProduct.setAdapter(productAdapter);
                            }
                            mSrlHomeLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.d("ERROR", t.getMessage(), t);
                    }
                });
    }

    private void showProductDetailActivity(Product product) {
        Intent productDetailActivity = new Intent(getActivity(), ProductDetailActivity.class);
        productDetailActivity.putExtra("product.title", product.getName());
        productDetailActivity.putExtra("product.description", product.getDescription());
        productDetailActivity.putExtra("product.photo", product.getPhotoUrl());
        startActivity(productDetailActivity);
    }
}
