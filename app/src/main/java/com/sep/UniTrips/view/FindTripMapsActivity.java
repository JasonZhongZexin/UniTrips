package com.sep.UniTrips.view;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sep.UniTrips.R;
import com.sep.UniTrips.presenter.Records;

import java.util.ArrayList;
import java.util.List;

public class FindTripMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    List<double[]> coords = null; //the coords of path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trip_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 获取路径信息
        Intent intent = getIntent();
        String coords_String = intent.getStringExtra("coords_string");
        //System.out.println("*************coords_String: " + coords_String);
        coords = Records.coords_string_to_double(coords_String);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-33.9661,151.10307);


        // 绘制路线图
        PolylineOptions lineOptions = new PolylineOptions();
        List<LatLng> positions = new ArrayList<LatLng>();
        for (int i = 0; i < coords.size(); i++) {
            double[] coord = coords.get(i);

            LatLng position = new LatLng(coord[0], coord[1]);
            //System.out.println(position.toString());
            positions.add(position);
        }
        // 放大以及定位到起点
        float maxZoom = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positions.get(0), maxZoom));

        mMap.addMarker(new MarkerOptions().position(positions.get(0)).title("Marker start"));
        mMap.addMarker(new MarkerOptions().position(positions.get(positions.size() - 1)).title("Marker end"));


        lineOptions.addAll(positions);
        lineOptions.width(3);

        mMap.addPolyline(lineOptions);
    }
}
