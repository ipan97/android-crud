package com.github.ipan97.kpexpress.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ipan97.kpexpress.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

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
            String title = bundle.getString("product.title");
            String description = bundle.getString("product.description");
            String photo = bundle.getString("product.photo");
            if (title != null) {
                mTvTitle.setText(title);
            }

            if (description != null) {
                mTvDescription.setText(description);
            }

            if (photo != null) {
                Glide.with(this)
                        .load(photo)
                        .apply(new RequestOptions().override(350, 550))
                        .into(mIvPhoto);
            }
        }
    }
}
