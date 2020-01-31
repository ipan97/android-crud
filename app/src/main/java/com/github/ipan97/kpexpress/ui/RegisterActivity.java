package com.github.ipan97.kpexpress.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.model.RegisterRequest;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;

    @BindView(R.id.et_email)
    EditText mEtEmail;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @BindView(R.id.tv_signin)
    TextView tvSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_signin)
    public void onClickTVSignin() {
        onBackPressed();
    }

    @OnClick(R.id.btn_register)
    public void onClickBtnRegister() {
        String name = mEtName.getText().toString();
        String phoneNumber = mEtPhoneNumber.getText().toString();
        String email = mEtPhoneNumber.getText().toString();
        String password = mEtPassword.getText().toString();
        RetrofitHttpClient.client().register(new RegisterRequest(
                name,
                phoneNumber,
                email,
                password
        )).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Register Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
