package com.example.yulicar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static SharedPreferences mSettings;
    private Button bSignIn;
    private Button bSignUp;
    private Button bMenuTest;

    private final String APP_PREFERENCES = "my_APP_PREFERENCES";
    public static final String APP_PREFERENCES_VISITED = "APP_PREFERENCES_VISITED";
    public static final String APP_PREFERENCES_USERNAME = "APP_PREFERENCES_USERNAME";
    public static final String APP_PREFERENCES_USERNUMBER = "APP_PREFERENCES_USERNUMBER";
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
        if (hasVisited) {
            startActivity (new Intent (MainActivity.this, Menu.class));
            MainActivity.this.finish ();
        } else setContentView (R.layout.activity_main);
    }
    private void checkFirstStart() {

        SharedPreferences sp = getSharedPreferences("hasVisited",
                Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа (Если вход первый то вернет false)
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            // Сработает если Вход первый

            //Ставим метку что вход уже был
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit(); //После этого hasVisited будет уже true и будет означать, что вход уже был
            setContentView (R.layout.activity_main);
            //Ниже запускаем активность которая нужна при первом входе

        } else {

            startActivity (new Intent (MainActivity.this, Menu.class));
            MainActivity.this.finish ();
            //setContentView (R.layout.central_activity);
            //Сработает если вход в приложение уже был
            //Ниже запускаем активность которая нужна при последующих входах
        }
    }

    public static void setHasVisited (boolean tOrF) {
        SharedPreferences.Editor e = mSettings.edit();
        e.putBoolean(MainActivity.APP_PREFERENCES_VISITED, tOrF);
        e.commit();
    }

    public static void setUserValues (String name, String number) {
        SharedPreferences.Editor e = mSettings.edit();
        e.putString (MainActivity.APP_PREFERENCES_USERNAME, name);
        Log.d("APP_PREFERENCES", APP_PREFERENCES_USERNAME);
        e.apply ();
        e.putString (MainActivity.APP_PREFERENCES_USERNUMBER, number);
        Log.d("APP_PREFERENCES", APP_PREFERENCES_USERNUMBER);
        e.apply ();
        Log.d("APP_PREFERENCES", APP_PREFERENCES_USERNUMBER);
        Log.d("APP_PREFERENCES", APP_PREFERENCES_USERNAME);
    }

}