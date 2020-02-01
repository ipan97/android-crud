package com.github.ipan97.kpexpress.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProductActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.et_price)
    EditText mEtPrice;

    @BindView(R.id.et_description)
    EditText mEtDescription;

    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;

    @BindView(R.id.btn_choose_photo)
    Button mBtnChoosePhoto;

    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("product.name");
            String description = bundle.getString("product.description");
            String price = bundle.getString("product.price");
            String photo = bundle.getString("product.photo");
            if (name != null) {
                mEtName.setText(name);
            }

            if (description != null) {
                mEtDescription.setText(description);
            }

            if (price != null) {
                mEtPrice.setText(price);
            }

            if (photo != null) {
                Glide.with(this)
                        .load(RetrofitHttpClient.BASE_IMAGE_URL + photo)
                        .apply(new RequestOptions().override(200, 200))
                        .into(mIvPhoto);
            }
        }
    }
}
