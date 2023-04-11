package com.example.appfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appfood.R;
import com.example.lib.common.Show;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SuccessCheckoutActivity extends AppCompatActivity {
    TextView txt_tenkhachhang,txt_email,txt_sodienthoai,txt_ghichu,txt_tongtien, txt_ngaydathang;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_checkout);
        getViewId();
        getThongTinKhachHang();
    }

    private void getThongTinKhachHang() {
        // Tạo đối tượng SimpleDateFormat với định dạng "yyyy-MM-dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Lấy ngày hiện tại
        Date now = new Date();

        // Định dạng ngày hiện tại theo định dạng "yyyy-MM-dd"
        String orderDate = dateFormat.format(now);

        txt_tenkhachhang.setText(ThongTinKhachHangActivity.user_name.getText().toString());
        txt_email.setText(ThongTinKhachHangActivity.user_email.getText().toString());
        txt_sodienthoai.setText(ThongTinKhachHangActivity.user_phone.getText().toString());
        txt_ghichu.setText(ThongTinKhachHangActivity.user_note.getText().toString());
        txt_ngaydathang.setText(orderDate);
        long thanhtien = Show.tinhTongTien();
        txt_tongtien.setText(decimalFormat.format(thanhtien)+" đ");
        Show.listGiohang.clear();
    }

    private void getViewId() {
        txt_tenkhachhang = findViewById(R.id.txt_tenkhachhang);
        txt_email = findViewById(R.id.txt_email);
        txt_sodienthoai = findViewById(R.id.txt_sodienthoai);
        txt_ghichu = findViewById(R.id.txt_ghichu);
        txt_ngaydathang = findViewById(R.id.txt_ngaydathang);
        txt_tongtien = findViewById(R.id.txt_tongtien);
    }

    public void ToHome(View view) {
        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(trangchu);
    }
}