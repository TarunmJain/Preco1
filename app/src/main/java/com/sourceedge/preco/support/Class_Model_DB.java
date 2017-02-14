package com.sourceedge.preco.support;

import android.print.PrintDocumentInfo;

import com.sourceedge.preco.location.model.ModelPrinters;
import com.sourceedge.preco.support.models.LocationDevices;
import com.sourceedge.preco.support.models.Locations;
import com.sourceedge.preco.support.models.ServiceOptions;
import com.sourceedge.preco.support.models.Services;

import java.util.ArrayList;

/**
 * Created by Centura User3 on 1/16/2017.
 */

public class Class_Model_DB {
    public static Locations SelectedPrinter=null;
    public static ArrayList<ModelPrinters> Printers= new ArrayList<ModelPrinters>();

    private static ArrayList<Services> serviceslist=new ArrayList<Services>();
    private static Services services=new Services();

    private static ArrayList<Locations> locationlist=new ArrayList<Locations>();
    private static Locations locations=new Locations();

    private static ArrayList<LocationDevices> locationDevicesList=new ArrayList<LocationDevices>();
    private static LocationDevices locationDevices=new LocationDevices();

    private static ArrayList<ServiceOptions> serviceOptionsList=new ArrayList<ServiceOptions>();
    private static ServiceOptions serviceOptions=new ServiceOptions();




    public static ArrayList<Locations> getLocationlist() {
        return locationlist;
    }

    public static void setLocationlist(ArrayList<Locations> locationlist) {
        Class_Model_DB.locationlist = locationlist;
    }

    public static Locations getLocations() {
        return locations;
    }

    public static void setLocations(Locations locations) {
        Class_Model_DB.locations = locations;
    }

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

    public static LocationDevices getLocationDevices() {
        return locationDevices;
    }

    public static void setLocationDevices(LocationDevices locationDevices) {
        Class_Model_DB.locationDevices = locationDevices;
    }

    public static ArrayList<LocationDevices> getLocationDevicesList() {
        return locationDevicesList;
    }

    public static void setLocationDevicesList(ArrayList<LocationDevices> locationDevicesList) {
        Class_Model_DB.locationDevicesList = locationDevicesList;
    }

    public static ServiceOptions getServiceOptions() {
        return serviceOptions;
    }

    public static void setServiceOptions(ServiceOptions serviceOptions) {
        Class_Model_DB.serviceOptions = serviceOptions;
    }

    public static ArrayList<ServiceOptions> getServiceOptionsList() {
        return serviceOptionsList;
    }

    public static void setServiceOptionsList(ArrayList<ServiceOptions> serviceOptionsList) {
        Class_Model_DB.serviceOptionsList = serviceOptionsList;
    }
}
