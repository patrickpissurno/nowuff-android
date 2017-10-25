package com.exe.jpg.nowuff.API.Response;

import com.exe.jpg.nowuff.API.Model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Patrick on 25/10/2017.
 */

public class GetUserResponse
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private UserModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserModel getData(){
        return data;
    }

    public void setData(UserModel data){
        this.data = data;
    }
}
