package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appfood.R;
import com.example.lib.InterfaceResponsitory.AppFoodMethods;
import com.example.lib.RetrofitClient;
import com.example.lib.common.Url;
import com.example.lib.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtRegister;
    EditText txtUsername, txtPassword;
    AppCompatButton btnLogin;

    AppFoodMethods appFoodMethods;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }
    public static String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    public static boolean comparePasswords(String inputPassword, String hashedPassword) {
        String encodedInputPassword = encodePassword(inputPassword);
        return encodedInputPassword.equals(hashedPassword);
    }
    private void initControl() {
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKiActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameText = txtUsername.getText().toString().trim();
                String passwordText = txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(userNameText)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập username", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập password", Toast.LENGTH_LONG).show();
                }  else {
                    compositeDisposable.add(appFoodMethods.POST_DangNhap(userNameText, passwordText)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    user -> {
                                        Utils.user_current = user.getResult().get(0);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "Vui lòng kiểm tra lại tài khoản hoặc mật khảu", Toast.LENGTH_LONG).show();
                                    }
                            ));
                }
            }
        });
    }
    private void initView() {
        appFoodMethods = RetrofitClient.getRetrofit(Url.AppFood_Url).create(AppFoodMethods.class);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtRegister = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getUsername() != null && Utils.user_current.getPassword() != null) {
            txtUsername.setText(Utils.user_current.getUsername());
            txtPassword.setText(Utils.user_current.getPassword());
        }
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}