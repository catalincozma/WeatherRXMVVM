package com.example.cozma.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cozma on 03.11.2017.
 */

public class SysModel {

    @SerializedName("pod")
    private String pod;

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}
