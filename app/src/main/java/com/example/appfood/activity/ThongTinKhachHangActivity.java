package com.example.appfood.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.functions.Consumer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appfood.R;
import com.example.lib.InterfaceResponsitory.AppFoodMethods;
import com.example.lib.RetrofitClient;
import com.example.lib.common.NetworkConnection;
import com.example.lib.common.Show;
import com.example.lib.common.Url;
import com.example.lib.common.Validate;
import com.example.lib.InterfaceResponsitory.AppFoodMethods;
import com.example.lib.model.KhachHang;
import com.example.lib.model.OrderResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    Toolbar toolbarThanhToan;
    static Button btn_xacnhanthanhtoan;
    public static EditText user_name;
    public static EditText user_fullname;
    static EditText user_email;
    static EditText user_phone;
    static EditText user_note;
    TextView textview_tongtien;
    static TextView message_name;
    static TextView message_email;
    static TextView message_phone;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AppFoodMethods appFoodMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        // Ánh xạ các thành phần UI và thiết lập Toolbar
        getViewId();
        actionToolbar();

        // Kiểm tra kết nối mạng
        if(NetworkConnection.isConnected(this)) {
            // Nếu có kết nối mạng, tiếp tục thực hiện các tác vụ khác
            tinhTongTienGioHang();
            xacnhanthanhtoan();

            // Lấy giá trị username từ SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            String fullname = sharedPreferences.getString("fullname", "");
            String email = sharedPreferences.getString("email", "");
            String phone = sharedPreferences.getString("phone", "");

            // Đưa giá trị username vào TextView
            TextView txtUsername = findViewById(R.id.user_name);
            TextView txtUserfullname = findViewById(R.id.user_fullname);
            TextView txtUseremail = findViewById(R.id.user_email);
            TextView txtUserphone = findViewById(R.id.user_phone);

//            Log.d("ThongTinKhachHa", "Username: " + username);
//            Log.d("ThongTinKhachHa", "Fullname: " + fullname);
//            Log.d("ThongTinKhachHa", "Email: " + email);
//            Log.d("ThongTinKhachHa", "Phone: " + phone);

            txtUsername.setText(username);
            txtUserfullname.setText(fullname);
            txtUseremail.setText(email);
            txtUserphone.setText(phone);
        } else {
            // Nếu không có kết nối mạng, hiển thị thông báo lỗi và kết thúc activity
            Show.Notify(this, getString(R.string.error_network));
            finish();
        }
    }

    public void xacnhanthanhtoan() {
        btn_xacnhanthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = user_name.getText().toString().trim();
                final String fullname = user_fullname.getText().toString().trim();
                final String email = user_email.getText().toString().trim();
                final String phone = user_phone.getText().toString().trim();
                final String totalPrice = String.valueOf(Show.tinhTongTien()).trim();
                final String note = user_note.getText().toString().trim();

                if (Validate.isValidName(name, message_name) &&
                        Validate.isValidEmail(email, message_email) &&
                        Validate.isValidPhone(phone, 10, message_phone)) {

                    compositeDisposable.add(appFoodMethods.POST_TaoDonHang(name, fullname, email, phone, totalPrice, note)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<OrderResponse>() {
                                @Override
                                public void accept(OrderResponse response) throws Throwable {
                                    if (response.isSuccess()) {
                                        Show.Notify(getApplicationContext(), getString(R.string.order_send_success));

                                        // Chuyển sang màn hình thành công
                                        Intent thanhcong = new Intent(getApplicationContext(), SuccessCheckoutActivity.class);
                                        startActivity(thanhcong);
                                    } else {
                                        Show.Notify(getApplicationContext(), response.getMessage());
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Throwable {
                                    Show.Notify(getApplicationContext(), getString(R.string.order_send_error));
                                    Log.e("Order", "Lỗi: " + throwable.getMessage());
                                }
                            }));
                } else {
                    Validate.isValidName(name, message_name);
                    Validate.isValidEmail(email, message_email);
                    Validate.isValidPhone(phone, 10, message_phone);
                }
            }
        });
    }



    private void getViewId() {
        toolbarThanhToan = findViewById(R.id.toolbarThanhToan);
        btn_xacnhanthanhtoan = findViewById(R.id.btn_xacnhanthanhtoan);
        user_name = findViewById(R.id.user_name);
        user_fullname = findViewById(R.id.user_fullname);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        textview_tongtien= findViewById(R.id.textview_tongtien);
        message_name= findViewById(R.id.message_name);
        message_email= findViewById(R.id.message_email);
        message_phone= findViewById(R.id.message_phone);
        user_note= findViewById(R.id.user_note);
        appFoodMethods = RetrofitClient.getRetrofit(Url.AppFood_Url).create(AppFoodMethods.class);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarThanhToan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThanhToan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void tinhTongTienGioHang() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textview_tongtien.setText(decimalFormat.format(Show.tinhTongTien())+" đ");
    }

    public void ToHome(View view) {
        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(trangchu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tinhTongTienGioHang();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tinhTongTienGioHang();
    }
}