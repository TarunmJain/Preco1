package com.sourceedge.preco.location.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 1/16/2017.
 */

public class ModelPrinters {

    String Title="";
    MarkerOptions marker= new MarkerOptions();
    LatLng Location;
    String Address;
    String Distance;
    String status;
    String PaperType;
    String ColorType;
    boolean Operator;

    public ModelPrinters(String title,String paperType,String colorType,boolean operator, MarkerOptions markerlocation,String address,String distance){
        Title=title;
        marker=markerlocation;
        Address=address;
        Distance=distance;
        status="";
        PaperType=paperType;
        ColorType=colorType;
        Operator=operator;
    }

    public ModelPrinters(String title, LatLng position){
        Title=title;
        Location=position;
    }

    public LatLng getLocation() {
        return Location;
    }

    public void setLocation(LatLng location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void setMarker(MarkerOptions marker) {
        this.marker = marker;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPaperType() {
        return PaperType;
    }

    public void setPaperType(String paperType) {
        PaperType = paperType;
    }

    public String getColorType() {
        return ColorType;
    }

    public void setColorType(String colorType) {
        ColorType = colorType;
    }

    public boolean isOperator() {
        return Operator;
    }

    public void setOperator(boolean operator) {
        Operator = operator;
    }
}
