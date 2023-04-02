package com.example.lib;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // Retrofit là một thư viện trong Android Studio được sử dụng để xử lý các kết nối mạng và truy vấn API
    // xác định đường dẫn và các loại yêu cầu như GET, POST, PUT, DELETE
    // hỗ trợ việc chuyển đổi dữ liệu từ JSON sang Java object và ngược lại,
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(String url) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    // url:  public static final String AppFood_Url = "http://"+ ipv4Address +"/AppFood/";
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
