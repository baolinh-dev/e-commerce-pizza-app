package com.example.lib.InterfaceResponsitory;

import com.example.lib.model.DanhMuc;
import com.example.lib.model.Mon;
import com.example.lib.model.User;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppFoodMethods {
    @GET("danhmuc.php")
    Observable<DanhMuc> GET_DanhMuc();

    @GET("monngaunhien.php")
    Observable<Mon> GET_MonNgauNhien();

    @POST("chitietdanhmuc.php")
    @FormUrlEncoded
    Observable<Mon> POST_MonTheoDanhMuc(
            @Field("madanhmuc") int madanhmuc
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<User> POST_DangKi(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") String phone
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<User> POST_DangNhap(
            @Field("username") String username,
            @Field("password") String password
    );
}
