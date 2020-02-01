package com.github.ipan97.kpexpress.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;

import java.io.File;
import java.math.BigDecimal;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.et_price)
    EditText mEtPrice;

    @BindView(R.id.et_description)
    EditText mEtDescription;

    @BindView(R.id.btn_choose_photo)
    Button mBtnChoosePhoto;

    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ButterKnife.bind(this);

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

        mBtnSubmit.setOnClickListener(v -> {
            String name = mEtName.getText().toString();
            String description = mEtDescription.getText().toString();
            String sPrice = mEtPrice.getText().toString();

            if (isValid(name, description)) {
                ProgressDialog dialog = ProgressDialog.show(this, "Loading...", "Wait while loading");

                RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
                RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), description);
                RequestBody requestPrice = RequestBody.create(MediaType.parse("text/plain"), sPrice);

                RetrofitHttpClient.client()
                        .addProduct(requestName, requestPrice, requestDescription, getPhoto())
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
        if (imagePath.getPath() != null) {
            File file = new File(getRealPathFromURI(imagePath));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        }
        return null;
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
            }
        }
    }
}
