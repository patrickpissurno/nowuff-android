package com.exe.jpg.nowuff.API.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Patrick on 25/10/2017.
 */

public class AuthResponse
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("authToken")
    @Expose
    private String authToken;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
