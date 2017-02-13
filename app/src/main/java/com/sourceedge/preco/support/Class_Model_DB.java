package com.sourceedge.preco.support;

import android.print.PrintDocumentInfo;

import com.sourceedge.preco.location.model.ModelPrinters;
import com.sourceedge.preco.support.models.Services;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 1/16/2017.
 */

public class Class_Model_DB {
    public static ModelPrinters SelectedPrinter=null;
    public static ArrayList<ModelPrinters> Printers= new ArrayList<ModelPrinters>();
    public static PrintDocumentInfo selectedattibutes;

    private static ArrayList<Services> serviceslist=new ArrayList<Services>();
    private static Services services=new Services();


    public static ArrayList<Services> getServiceslist() {
        return serviceslist;
    }

    public static void setServiceslist(ArrayList<Services> serviceslist) {
        Class_Model_DB.serviceslist = serviceslist;
    }

    public static Services getServices() {
        return services;
    }

    public static void setServices(Services services) {
        Class_Model_DB.services = services;
    }
}
