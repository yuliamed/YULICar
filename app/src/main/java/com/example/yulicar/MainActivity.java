package com.example.yulicar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences mSettings;
    private Button bSignIn;
    private Button bSignUp;
    private Button bMenuTest;

    private final String APP_PREFERENCES = "my_APP_PREFERENCES";
    public static final String APP_PREFERENCES_VISITED = "APP_PREFERENCES_VISITED";
    //public static final String APP_PREFERENCES_USERNAME = "APP_PREFERENCES_USERNAME";
    //public static final String APP_PREFERENCES_USERNUMBER = "APP_PREFERENCES_USERNUMBER";
    public static final String APP_PREFERENCES_USERID = "APP_PREFERENCES_USERID";
    public boolean hasVisited;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        //checkFirstStart ();

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        hasVisited = mSettings.getBoolean(APP_PREFERENCES_VISITED, false);
        checkSignIn ();
        super.onCreate (savedInstanceState);
        //setContentView (R.layout.activity_main);

        bSignIn = (Button)findViewById (R.id.first_sing_in);
        bSignUp = (Button) findViewById (R.id.first_sing_up);
        bMenuTest=(Button) findViewById (R.id.bTest);
    }

    public void click (View v) {
        switch(v.getId ()){
            case R.id.first_sing_in:
                startActivity (new Intent (MainActivity.this, SingIn.class));
                break;
            case R.id.first_sing_up:
                startActivity (new Intent (MainActivity.this, SingUp.class));
                break;
            case R.id.bTest:
                startActivity (new Intent (MainActivity.this, Menu.class));
                break;
        }
    }
    private void checkSignIn() {
        DBManeger dbManeger = new DBManeger (getApplicationContext ());
        List<User> users = dbManeger.dao.getUsers ();
        if (users.isEmpty () || !hasVisited) {
            setContentView (R.layout.activity_main);
        }
        else {
            startActivity (new Intent (MainActivity.this, Menu.class));
            MainActivity.this.finish ();
        }
    }

    public static void setHasVisited (boolean tOrF) {
        SharedPreferences.Editor e = mSettings.edit();
        e.putBoolean(MainActivity.APP_PREFERENCES_VISITED, tOrF);
        e.commit();
    }

    public static void setUserValues (int userID) {
        SharedPreferences.Editor e = mSettings.edit();
        e.putInt (MainActivity.APP_PREFERENCES_USERID, userID);
        //e.putString (MainActivity.APP_PREFERENCES_USERNUMBER, number);
        //Log.d("APP_PREFERENCES", APP_PREFERENCES_USERNAME);
        //Log.d("APP_PREFERENCES", APP_PREFERENCES_USERNUMBER);
        e.commit ();
    }

}