package com.exe.jpg.nowuff.API.Response;

import com.exe.jpg.nowuff.API.Model.AlertModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Patrick on 25/10/2017.
 */

public class GetAlertResponse
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private AlertModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AlertModel getData() {
        return data;
    }

    public void setData(AlertModel data) {
        this.data = data;
    }
}
