package com.github.ipan97.kpexpress.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ipan97.kpexpress.R;
import com.github.ipan97.kpexpress.data.DataManager;
import com.github.ipan97.kpexpress.model.ApiResponse;
import com.github.ipan97.kpexpress.model.LoginRequest;
import com.github.ipan97.kpexpress.model.LoginResponse;
import com.github.ipan97.kpexpress.network.ApiEndpoint;
import com.github.ipan97.kpexpress.network.RetrofitHttpClient;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText mEtEmail;

    @BindView(R.id.et_password)
    EditText mEtPassword;

    @BindView(R.id.tv_signup)
    TextView mTvSignup;

    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

/*        String accessToken = DataManager.data(this).getString("access_token", null);
        if (!"".equals(accessToken)) {
            startActivity(new Intent(this, MainActivity.class));
        }*/
    }

    @OnClick(R.id.tv_signup)
    public void onClickViewRegister() {
        Intent registerActivity = new Intent(this, RegisterActivity.class);
        startActivity(registerActivity);
    }

    @OnClick(R.id.btn_login)
    public void onClickButtonLogin() {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        if ("".equals(email)) {
            mEtEmail.setError("Email required !");
        }
        if ("".equals(password)) {
            mEtPassword.setError("Password required !");
        }
        if ("".equals(email) && "".equals(password)) {
            mEtEmail.setError("Email required !");
            mEtPassword.setError("Password required !");
        }
        if (!"".equals(email) && !"".equals(password)) {
            ProgressDialog dialog = ProgressDialog.show(this, "Loading...", "Wait while loading");
            RetrofitHttpClient.client().login(new LoginRequest(email, password))
                    .enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    String json = new Gson().toJson(response.body().getData());
                                    LoginResponse loginResponse = new Gson().fromJson(json, LoginResponse.class);
                                    // save access_token to share preferences
                                    DataManager.edit(getApplicationContext())
                                            .putString("access_token", loginResponse.getAccessToken())
                                            .commit();

                                    // close dialog before start activity
                                    dialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Log.d("ERROR", t.getMessage(), t);
                            Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
        }

    }
}
