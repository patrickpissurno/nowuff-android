package com.exe.jpg.nowuff.API.Response;

import com.exe.jpg.nowuff.API.Model.AlertModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Patrick on 25/10/2017.
 */

public class GetAlertsResponse
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<AlertModel> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AlertModel> getData() {
        return data;
    }

    public void setData(List<AlertModel> data) {
        this.data = data;
    }
}
