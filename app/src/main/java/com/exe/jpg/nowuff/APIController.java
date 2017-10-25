package com.exe.jpg.nowuff;

import android.util.Log;

import com.exe.jpg.nowuff.API.APIService;
import com.exe.jpg.nowuff.API.AuthService;
import com.exe.jpg.nowuff.API.LoggingInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Patrick on 25/10/2017.
 */

public class APIController
{
    public static final String SERVER_ADDRESS = "http://10.1.105.204:3000/";
    public static final String AUTH_ENDPOINT = SERVER_ADDRESS;
    public static final String API_ENDPOINT = SERVER_ADDRESS + "api/";
    public static final String URL_PREFIX = "http://";


    public static final String FB_ID = "1499047350183346";
    public static final String FB_AUTH = "EAAMUyQYZAPRQBAIVkKKkzXQ3lBx2nEZCsoKW8T8awzvmDrck3B93CR2lecPXq1LqWqcoRLmEaFoiqrd5I1Qvr6ayJ5MeHEX2ZAWBG0BnJhMpFsTRiTdLrZCtBf2IKMkJhwZA4s3SskwkEuYWXncKUw4bBKFcrvw4ZD";

    private static OkHttpClient.Builder getOkHttpClient(){
        return new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(chain -> {
//                    Request rq = chain.request();
//                    Log.d("REQ", rq.toString());
//                    Response r = chain.proceed(rq);
//                    Log.d("RESP", r.toString());
//                    Log.d("RESP B", r.body().string());
//                    return r;
//                })
                .addInterceptor(new LoggingInterceptor())
                ;
    }

    public static AuthService GetAuthService(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AUTH_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient().build())
                .build();

        return retrofit.create(AuthService.class);
    }

    public static APIService GetUserService(){
        final OkHttpClient client = getOkHttpClient()
                .addInterceptor(chain -> {
                    final Request original = chain.request();

                    final Request request = original.newBuilder()
                            .header("auth-token", SessionController.getInstance().getAuthToken())
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(APIService.class);
    }
}
