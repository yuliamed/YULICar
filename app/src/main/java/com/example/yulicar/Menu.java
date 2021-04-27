package com.example.yulicar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.central_activity);

        BottomNavigationView bottomNavigationView= findViewById (R.id.navigation_view);
        /*bottomNavigationView.setSelectedItemId (R.id.searchFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem item) {
                switch (item.getItemId ()) {
                    case R.id.helpFragment:
                        startActivity (new Intent (getApplicationContext (), HelpFragment.class));
                        overridePendingTransition (0, 0);
                        return true;
                    case R.id.searchFragment:
                        return true;
                    case R.id.userFragment:
                        startActivity (new Intent (getApplicationContext (), UserFragment.class));
                        overridePendingTransition (0, 0);
                        return true;
                }
                return false;
            }
        });*/

        bottomNavigationView.setOnNavigationItemSelectedListener (navigationItemSelectedListener);
        getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_conteiner, new CentralFragment ()).commit ();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener () {
        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId ()) {
                case R.id.helpFragment:
                    selectedFragment = new HelpFragment ();
                    break;
                case R.id.searchFragment:
                    selectedFragment = new CentralFragment ();
                    break;
                case R.id.userFragment:
                    selectedFragment = new UserFragment ();
                    break;

            }
            getSupportFragmentManager ().beginTransaction ().replace (R.id.fragment_conteiner, selectedFragment).commit ();
            return false;
        }
    };
}

