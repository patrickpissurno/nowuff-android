package com.exe.jpg.nowuff.API;

import com.exe.jpg.nowuff.API.Response.AuthResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Patrick on 25/10/2017.
 */

public interface AuthService
{
    @FormUrlEncoded
    @POST("auth")
    Observable<AuthResponse> user(@Field("facebook_id") String facebookId, @Field("auth_token") String authToken);
}
