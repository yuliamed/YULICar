package com.example.yulicar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.MyDB;
import com.example.yulicar.db.MyDao;
import com.example.yulicar.entities.Trip;
import com.example.yulicar.entities.User;
import com.google.android.material.snackbar.Snackbar;
import com.santalu.maskara.widget.MaskEditText;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SingIn extends Activity {
    //MyDB db;
    //MyDao dao;
    DBManeger dbManeger;
    private MaskEditText phNumber;
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
        //db = Room.databaseBuilder (getApplicationContext (), MyDB.class, "db").allowMainThreadQueries ().build ();
        //dao = db.getMyDao ();
        //dbManeger.openDB (getApplicationContext ());
        dbManeger = new DBManeger (getApplicationContext ());
        mSettings = getSharedPreferences(MainActivity.APP_PREFERENCES_VISITED, Context.MODE_PRIVATE);
        //User user1 = new User(336276633, "Юля");
        //dao.addUser (user1);
        super.onStart ();
    }

    //обработчик кнопки на Activity
    public void signIn (View view) {
        List<User> users = dbManeger.dao.getUsers ();
        for (User u : users) {
            Log.d("DB-TEST", phNumber.getUnMasked ().toString () + " " + u.getPhNumber ().toString ());
            if (phNumber.getUnMasked ().equals (u.getPhNumber ().toString ()) ) {
                Log.d("DB-TEST", phNumber.getUnMasked ().toString () + u.getPhNumber ().toString ());
                startActivity (new Intent (SingIn.this, Menu.class));
                MainActivity.setUserValues (u.getName ().toString (), phNumber.getMasked ());
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
        //phNumber.getUnMasked (), name.getText ().toString ();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
    }
}

















