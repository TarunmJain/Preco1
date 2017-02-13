package com.sourceedge.preco.support.models;

/**
 * Created by Centura User3 on 2/4/2017.
 */

public class Services {
    private String Id;
    private String Name;
    private boolean DeviceRequired;
    private String ImageURL;

    public Services(){
        Id="";
        Name="";
        DeviceRequired=false;
        ImageURL="";
    }

    public boolean isDeviceRequired() {
        return DeviceRequired;
    }

    public void setDeviceRequired(boolean deviceRequired) {
        DeviceRequired = deviceRequired;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
