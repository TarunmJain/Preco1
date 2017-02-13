package com.sourceedge.preco.support.models;

/**
 * Created by Centura User3 on 2/13/2017.
 */

public class Options {
    private String Id;
    private String SelectedOption;

    public Options(){
        Id="";
        SelectedOption="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSelectedOption() {
        return SelectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        SelectedOption = selectedOption;
    }
}
