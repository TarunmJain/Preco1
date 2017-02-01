package com.sourceedge.preco.support;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sourceedge.preco.location.model.ModelPrinters;

/**
 * Created by Centura User3 on 1/16/2017.
 */

public class FeedData {

    public static void printersData(){
        String adtrssMes="No.5/1,2nd Floor,Agarwal Bhawan,18Th Cross,\n" +
                "Opp.Bus Stand," +
                "Malleswaram,\n" +
                "Bangalore-560055";
        String addressPeset="No 6,2nd Floor ,\n" +
                "CSE Department,\n" +
                "Bangalore -560003";
        String adressBms="No 8,3 Floor \n" +
                ", EC Department ,\n" +
                "Malleswaram,Bangalore - 560003";

        Class_Model_DB.Printers.add(new ModelPrinters("MES College",new MarkerOptions().position(new LatLng(13.021745, 77.554492)).icon(BitmapDescriptorFactory.defaultMarker()),adtrssMes,"1.2 kms"));
        Class_Model_DB.Printers.add(new ModelPrinters("Pesit College",new MarkerOptions().position(new LatLng(13.005616,77.579021)).icon(BitmapDescriptorFactory.defaultMarker()),addressPeset,"8.1 kms"));
        Class_Model_DB.Printers.add(new ModelPrinters("BMS College",new MarkerOptions().position(new LatLng(13.002346,77.582461)).icon(BitmapDescriptorFactory.defaultMarker()),adressBms,"6.9 kms"));
       // Class_Model_DB.Printers.add(new ModelPrinters("Print Xpress",new MarkerOptions().position(new LatLng(13.001962,77.581759)).icon(BitmapDescriptorFactory.defaultMarker())));
    }

}
