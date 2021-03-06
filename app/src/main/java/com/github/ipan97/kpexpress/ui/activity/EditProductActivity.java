package com.github.ipan97.kpexpress.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id = bundle.getString("product.id");
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

            // Choose Photo
            mBtnChoosePhoto.setOnClickListener(v -> {

                if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    );
                    startActivityForResult(intent, 0);
                } else {
                    EasyPermissions.requestPermissions(
                            this,
                            "This application need your permission to access photo gallery.",
                            991,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    );
                }
            });

            // Submit edit product
            mBtnSubmit.setOnClickListener(v -> {
                String textName = mEtName.getText().toString();
                String textDescription = mEtDescription.getText().toString();
                String textPrice = mEtPrice.getText().toString();

                if (isValid(textName, textDescription)) {
                    ProgressDialog dialog = ProgressDialog.show(this, "Loading...", "Wait while loading");

                    RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), textName);
                    RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), textDescription);
                    RequestBody requestPrice = RequestBody.create(MediaType.parse("text/plain"), textPrice);
                    RetrofitHttpClient.client()
                            .updateProduct(id, requestName, requestPrice, requestDescription, getPhoto())
                            .enqueue(new Callback<ApiResponse>() {
                                @Override
                                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                                    if (response.isSuccessful()) {
                                        dialog.dismiss();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                }

                                @Override
                                public void onFailure(Call<ApiResponse> call, Throwable t) {
                                    Log.d("ERROR", t.getMessage(), t);
                                    dialog.dismiss();
                                }
                            });
                }
            });
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private MultipartBody.Part getPhoto() {
        if (imagePath != null) {
            File file = new File(getRealPathFromURI(imagePath));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        } else {
            RequestBody file = RequestBody.create(MultipartBody.FORM, "");
            return MultipartBody.Part.createFormData("photo", "", file);
        }
    }

    private boolean isValid(String name, String description) {
        if ("".equals(name)) {
            mEtName.setError("Name not null!");
            return false;
        } else {
            mEtName.setError(null);
        }

        if ("".equals(description)) {
            mEtDescription.setError("Description not null!");
            return false;
        } else {
            mEtDescription.setError(null);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imagePath = data.getData();
                Glide.with(this)
                        .load(getRealPathFromURI(imagePath))
                        .apply(new RequestOptions().override(200, 200))
                        .into(mIvPhoto);
            }
        }
    }
}
