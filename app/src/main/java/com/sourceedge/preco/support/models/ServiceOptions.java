package com.sourceedge.preco.support.models;

/**
 * Created by Centura User3 on 2/4/2017.
 */

public class ServiceOptions {
    private String Id;
    private String Name;
    private String Options;

    public ServiceOptions(){
        Id="";
        Name="";
        Options="";
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
        return Options;
    }

    public void setOptions(String options) {
        Options = options;
    }
}
