package com.sourceedge.preco.support.models;

/**
 * Created by Centura User3 on 2/4/2017.
 */

public class LocationDevices {
    private String Id;
    private String Name;

    public LocationDevices(){
        Id="";
        Name="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
