package com.sep.UniTrips.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sep.UniTrips.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

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
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_setting:
                    SettingFragment settingFragment = new SettingFragment();
                    FragmentTransaction settingFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    settingFragmentTransaction.replace(R.id.fragment_container,settingFragment,"HomeFragment");
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(findViewById(R.id.fragment_container)!=null){
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction homeFragmentTransaction = getSupportFragmentManager().beginTransaction();
            homeFragmentTransaction.replace(R.id.fragment_container,homeFragment,"HomeFragment");
            homeFragmentTransaction.commit();

        }
    }

}
