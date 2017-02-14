package com.sourceedge.preco.location.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sourceedge.preco.R;
import com.sourceedge.preco.location.model.ModelPrinters;
import com.sourceedge.preco.support.Class_Model_DB;
import com.sourceedge.preco.support.models.Locations;
import com.sourceedge.preco.uploadfile.controller.UploadFile;

import java.util.ArrayList;
import java.util.Locale;

import static com.sourceedge.preco.support.Class_Model_DB.SelectedPrinter;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Toolbar toolbar;

    public static GoogleMap mMap;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectedPrinter != null)
                    startActivity(new Intent(MapsActivity.this, UploadFile.class));
                else
                    Toast.makeText(MapsActivity.this, "Please Select A Machine", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(13.021745, 77.554492))      // Sets the center of the map to Mountain View
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();
        /*if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);*/
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        /*for (ModelPrinters printer : Class_Model_DB.Printers) {
            mMap.addMarker(printer.getMarker()).setTitle(printer.getTitle());
        }*/
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                Class_Model_DB.SelectedPrinter=new Locations(arg0.getTitle(),arg0.getPosition());
                return true;
            }

        });
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
