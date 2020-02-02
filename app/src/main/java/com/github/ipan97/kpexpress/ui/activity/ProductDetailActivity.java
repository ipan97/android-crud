package com.github.ipan97.kpexpress.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_price)
    TextView mTvPrice;

    @BindView(R.id.tv_description)
    TextView mTvDescription;

    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("product.name");
            String price = bundle.getString("product.price");
            String description = bundle.getString("product.description");
            String photo = RetrofitHttpClient.BASE_IMAGE_URL + bundle.getString("product.photo");
            if (name != null) {
                mTvName.setText(name);
            }

            if (price != null) {
                mTvPrice.setText(price);
            }

            if (description != null) {
                mTvDescription.setText(description);
            }

            Glide.with(this)
                    .load(photo)
                    .apply(new RequestOptions().override(350, 550))
                    .into(mIvPhoto);
        }
    }
}
