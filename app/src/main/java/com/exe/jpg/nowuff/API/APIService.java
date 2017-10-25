package com.exe.jpg.nowuff.API;

import com.exe.jpg.nowuff.API.Model.AlertModel;
import com.exe.jpg.nowuff.API.Response.BaseResponse;
import com.exe.jpg.nowuff.API.Response.GetAlertResponse;
import com.exe.jpg.nowuff.API.Response.GetAlertsResponse;
import com.exe.jpg.nowuff.API.Response.GetUserResponse;
import com.exe.jpg.nowuff.API.Response.PostAlertResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Patrick on 25/10/2017.
 */

public interface APIService
{
    @GET("users/{id}")
    Observable<GetUserResponse> getUserData (@Path("id") long id);
    @GET("alerts")
    Observable<GetAlertsResponse> getAlertsData ();
    @GET("alert/{id}")
    Observable<GetAlertResponse> getAlertData (@Path("id") long id);
    @FormUrlEncoded
    @POST("users/{id}/campus")
    Observable<BaseResponse> updateUserCampus (@Path("id") long id, @Field("campus") int campus);
    @FormUrlEncoded
    @POST("users/{id}/fcm_token")
    Observable<BaseResponse> updateUserFcmToken (@Path("id") long id, @Field("value") String value);
    @FormUrlEncoded
    @POST("alerts")
    Observable<PostAlertResponse> postAlert (@Field("text") String text, @Field("campus") int campus);
}
