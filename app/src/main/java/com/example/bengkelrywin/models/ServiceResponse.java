package com.example.bengkelrywin.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceResponse {
    private String message;

    @SerializedName("service")
    private Service service;

    @SerializedName("data")
    private List<Service> serviceList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }


}
