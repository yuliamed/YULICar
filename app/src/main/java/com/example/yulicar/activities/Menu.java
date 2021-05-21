package com.example.yulicar.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.yulicar.R;
import com.example.yulicar.fragments.CentralFragment;
import com.example.yulicar.fragments.HelpFragment;
import com.example.yulicar.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Menu extends AppCompatActivity {

    final Fragment search = new CentralFragment ();
    final Fragment user = new UserFragment ();
    final Fragment help = new HelpFragment();
    Fragment active = search;
    final FragmentManager fm = getSupportFragmentManager ();
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.central_activity);

        BottomNavigationView navigation = findViewById (R.id.navigation_view);
        navigation.setOnNavigationItemSelectedListener (navigationItemSelectedListener);
        //fm.beginTransaction ().add (R.id.fragment_conteiner, help).hide(help).commit ();
        //fm.beginTransaction ().add (R.id.fragment_conteiner, user).hide(user).commit ();
        fm.beginTransaction ().add (R.id.fragment_conteiner, search).commit ();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener () {
        @Override
        public boolean onNavigationItemSelected (@NonNull MenuItem item) {
            switch (item.getItemId ()) {
                case R.id.helpFragment:
                    //fm.beginTransaction ().hide (item.getActionView ).show (help).commit ();
                    active = help;
                    fm.beginTransaction ().replace (R.id.fragment_conteiner, active).commit ();
                    return true;
                case R.id.searchFragment:
                    //fm.beginTransaction ().hide (active).show (search).commit ();
                    active = search;
                    fm.beginTransaction ().replace (R.id.fragment_conteiner, active).commit ();
                    return true;
                case R.id.userFragment:
                    //fm.beginTransaction ().hide (active).show (user).commit ();
                    active = user;
                    fm.beginTransaction ().replace (R.id.fragment_conteiner, active).commit ();
                    return true;
            }
            return false;
        }
    };
}

