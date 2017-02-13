package com.sourceedge.preco.support.models;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 2/13/2017.
 */

public class SubmitOrder {
    private String ServiceId;
    private String LocationId;
    private String DeviceId;
    private String Time;
    private String Units;
    private String Qty;
    private String CouponCode;
    private String FileId;
    private ArrayList<Options> Options;

    public SubmitOrder(){
        ServiceId="";
        LocationId="";
        DeviceId="";
        Time="";
        Units="";
        Qty="";
        CouponCode="";
        FileId="";
        Options=new ArrayList<Options>();
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getFileId() {
        return FileId;
    }

    public void setFileId(String fileId) {
        FileId = fileId;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }

    public ArrayList<com.sourceedge.preco.support.models.Options> getOptions() {
        return Options;
    }

    public void setOptions(ArrayList<com.sourceedge.preco.support.models.Options> options) {
        Options = options;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }
}
