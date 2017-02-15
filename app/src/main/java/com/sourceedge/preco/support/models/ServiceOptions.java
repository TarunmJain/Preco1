package com.sourceedge.preco.support.models;

/**
 * Created by Centura User3 on 2/4/2017.
 */

public class ServiceOptions {
    private String Id;
    private String Name;
    private String OptionValues;

    public ServiceOptions(){
        Id="";
        Name="";
        OptionValues="";
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

    public String getOptions() {
        return OptionValues;
    }

    public void setOptions(String options) {
        OptionValues = options;
    }
}
