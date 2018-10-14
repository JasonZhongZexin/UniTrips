/**
 * Copyright (c) 2018. [Zexin Zhong]
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions an limitations under the License.
 */


package com.sep.UniTrips.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sep.UniTrips.R;
import com.sep.UniTrips.model.UserSetting.UserProfile;
import com.sep.UniTrips.model.UserSetting.UserSettingTaskManager;

import java.util.List;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

//    private FloatingActionButton mAddEventButton;
    // 交通方式
    private String userTransport = null;
    // 当前位置
    private Location location = null;
    // 到达路径
    private List<double[]> coords_double = null;

    private LocationManager locationManager = null;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private UserSettingTaskManager mTaskManager;

    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onLocationChanged(Location location) {
            location.getAccuracy();//精确度
            setLocation( location );
        }
    };


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction homeFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    homeFragmentTransaction.replace(R.id.fragment_container,homeFragment,"HomeFragment");
                    homeFragmentTransaction.commit();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_setting:
                    SettingFragment settingFragment = new SettingFragment();
                    FragmentTransaction settingFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    settingFragmentTransaction.replace(R.id.fragment_container,settingFragment,"SettingFragment"); // 这里的tag不对
                    settingFragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAddEventButton = findViewById(R.id.addEventBtn);
//        mAddEventButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
//                startActivity(intent);
//            }
//        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(findViewById(R.id.fragment_container)!=null){
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction homeFragmentTransaction = getSupportFragmentManager().beginTransaction();
            homeFragmentTransaction.replace(R.id.fragment_container,homeFragment,"HomeFragment");
            homeFragmentTransaction.commit();

        }
        getLocation(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Toast.makeText(MainActivity.this, "do refresh", Toast.LENGTH_SHORT).show();
                //TODO finish refresh logic here, but please don't delete this toast cause test code will check it's content

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 检查获取地理位置的权限
     */
    public void checkLocationPermission () {
        // check permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

    }

    /**
     * 获取GPS实时地理位置
     * @param context
     */
    public void getLocation (Context context) {
        // get location manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // get providers: either GPS or network
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = locationManager.GPS_PROVIDER;

        checkLocationPermission();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // get location
        Location location = locationManager.getLastKnownLocation( locationProvider );
        if (location != null) {
            setLocation( location );
        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, mListener);

    }

    public String getUserTransport () {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("User Profile");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userTransport = dataSnapshot.getValue(UserProfile.class).getPreferredTransport();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return userTransport;
    }

    public void setUserTransport (String userTransport) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference ref = mDatabase.child("users").child(currentUser.getUid()).child("User Profile").child("preferredTransport");
        ref.setValue(userTransport);
        this.userTransport = userTransport;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() { return this.location; }

    public void setCoords_double (List<double[]> coords_double) { this.coords_double = coords_double; }

    public List<double[]> getCoords_double () { return coords_double; }

}
