package com.sourceedge.preco.support.models;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class KeyValuePair {
    private String Key;
    private String Value;

    public KeyValuePair(){
        Key="";
        Value="";
    }

    public KeyValuePair(String key, String value) {
        Key = key;
        Value = value;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
