package com.example.lib.InterfaceResponsitory;

import com.example.lib.model.DanhMuc;
import com.example.lib.model.KhachHang;
import com.example.lib.model.Mon;
import com.example.lib.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppFoodMethods {
    @GET("danhmuc.php")
    Observable<DanhMuc> GET_DanhMuc();

    @GET("monngaunhien.php")
    Observable<Mon> GET_MonNgauNhien();

    @GET("user.php")
    Observable<List<KhachHang>> GET_KhachHang(@Query("search") String searchKeyword);

    @POST("chitietdanhmuc.php")
    @FormUrlEncoded
    Observable<Mon> POST_MonTheoDanhMuc(
            @Field("madanhmuc") int madanhmuc
    );

    @POST("dangkimobile.php")
    @FormUrlEncoded
    Observable<User> POST_DangKi(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("role") String role
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<User> POST_DangNhap(
            @Field("username") String username,
            @Field("password") String password
    );
}
