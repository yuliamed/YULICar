package com.example.yulicar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bSignIn;
    private Button bSignUp;
    private Button bMenuTest;
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        checkFirstStart ();
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

        }else {

            startActivity (new Intent (MainActivity.this, Menu.class));
            MainActivity.this.finish ();
            //setContentView (R.layout.central_activity);
            //Сработает если вход в приложение уже был
            //Ниже запускаем активность которая нужна при последующих входах
        }
    }
}