package com.example.yulicar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.entities.User;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SingIn extends Activity {
    //MyDB db;
    //MyDao dao;
    DBManeger dbManeger;
    private MaskedEditText phNumber;
    private TextView test;
    public SharedPreferences mSettings;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_in);
        phNumber = findViewById (R.id.etPhone);
        test = (TextView) findViewById (R.id.test);
    }

    @Override
    protected void onStart () {
        dbManeger = new DBManeger (getApplicationContext ());
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES_VISITED, Context.MODE_PRIVATE);
        super.onStart ();
    }

    //обработчик кнопки на Activity
    public void signIn (View view) {
        List<User> users = dbManeger.dao.getUsers ();
        for (User u : users) {
            Log.d("DB-TEST", phNumber.getUnmaskedText ().toString () + " " + u.getPhNumber ().toString ());
            if (phNumber.getUnmaskedText ().equals (u.getPhNumber ().toString ()) ) {
                Log.d("DB-TEST", phNumber.getUnmaskedText ().toString () + u.getPhNumber ().toString ());
                startActivity (new Intent (SingIn.this, Menu.class));
                MainActivity.setUserValues (u.getPhNumber ());
                MainActivity.setHasVisited(true);
                //После этого hasVisited будет уже true и будет означать, что вход уже был
                //setContentView (R.layout.activity_main);
                finishAffinity();
                return;
            }
        }
        Snackbar.make (view,
                "Такой пользователь не зарегистрирован",
                Snackbar.LENGTH_LONG).show ();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
    }
}

















