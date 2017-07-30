package com.example.nikhil.sbihackathon.fragment;


import java.io.Serializable;

/**
 * Created by root on 10/3/16.
 */
public class ProvidersData implements Serializable{
/*    "provider_id": 0,
            "provider_name": "PAY2ALL",
            "provider_code": "PAY2ALL",
            "service": "PAY2ALL",
            "provider_image": "",
            "status": "Success"*/

    private String provider_id;

    private String provider_name;

    private String provider_code;

    private String service;

    private String provider_image;

    private String status,amount,number;

    public ProvidersData(String provider_id, String amount, String number) {
        this.provider_id = provider_id;
        this.amount = amount;
        this.number = number;
    }

    public ProvidersData(String provider_id, String provider_name, String provider_code, String service, String provider_image, String status) {
        this.provider_id = provider_id;
        this.provider_name = provider_name;
        this.provider_code = provider_code;
        this.service = service;
        this.provider_image = provider_image;
        this.status = status;
    }

    public ProvidersData() {

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_code() {
        return provider_code;
    }

    public void setProvider_code(String provider_code) {
        this.provider_code = provider_code;
    }

    public String getProvider_image() {
        return provider_image;
    }

    public void setProvider_image(String provider_image) {
        this.provider_image = provider_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
