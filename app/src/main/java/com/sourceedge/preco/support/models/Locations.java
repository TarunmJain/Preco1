package com.sourceedge.preco.support.models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Centura User3 on 2/4/2017.
 */

public class Locations {
    private String Id;
    private String Name;
    private Double Latitude;
    private Double Longitude;

    public Locations(){
        Id="";
        Name="";
        Latitude=0.0;
        Longitude=0.0;
    }
    public Locations(String title, LatLng position){
        Name=title;
        Latitude=position.latitude;
        Longitude=position.longitude;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
