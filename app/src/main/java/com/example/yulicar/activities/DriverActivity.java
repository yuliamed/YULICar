package com.example.yulicar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yulicar.R;
import com.google.android.material.snackbar.Snackbar;

public class DriverActivity extends AppCompatActivity {
    private Button addTrip, viewTrips, exit;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        setContentView (R.layout.driver_menu);
        super.onCreate (savedInstanceState);
        addTrip = (Button) findViewById (R.id.driver_add_trip);
        viewTrips = (Button) findViewById (R.id.driver_look_trips);
        exit = (Button) findViewById (R.id.driver_sing_off);
    }

    public void driver_click (View v) {
        switch(v.getId ()){
            case R.id.driver_add_trip:
                startActivity (new Intent (this, AddTrip.class));
                break;
            case R.id.driver_look_trips:
                Snackbar.make (v,"ВСё скоро будет", Snackbar.LENGTH_LONG).show ();
                break;
            case R.id.driver_sing_off:
                Snackbar.make (v,"ВСё скоро будет", Snackbar.LENGTH_LONG).show ();
                startActivity (new Intent (this, MainActivity.class));
                this.finish ();
                break;
        }
    }
}
