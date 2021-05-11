package com.example.yulicar;

import android.app.Activity;
import android.content.Intent;
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
import com.example.yulicar.entities.User;
import com.google.android.material.snackbar.Snackbar;
import com.santalu.maskara.widget.MaskEditText;

import java.util.List;

public class SingUp extends Activity {
    //MyDB db;
    //MyDao dao;
    DBManeger dbManeger;
    private MaskEditText phNumber;
    private EditText name;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_up);
        //dbManeger.openDB (getApplicationContext ());
        name = (EditText) findViewById (R.id.etName);
        phNumber = findViewById (R.id.etPhone);
    }

    /*@Override
    protected void onResume () {
        super.onResume ();

    }*/

    @Override
    protected void onStart () {
        //db = Room.databaseBuilder (getApplicationContext (), MyDB.class, "db").allowMainThreadQueries ().build ();
        //dao = db.getMyDao ();
        super.onStart ();
    }

    public void signUp (View view) {
        Log.d("DB-TEST-SingUp", "Start SignUp");

        if (phNumber.getUnMasked ().length () == 0 || phNumber.getUnMasked ().length () < 9){
            Log.d("DB-TEST-SingUp", "wrong phNumber");
            phNumber.setBackgroundResource (R.drawable.fields_red);
            //Snackbar.make (view, "Поле номера телефона не заполнено", Snackbar.LENGTH_LONG);
            return;
        } else phNumber.setBackgroundResource (R.drawable.fields);
        if (name.getText ().length () == 0) {
            Log.d("DB-TEST-SingUp", "wrong name");
            Snackbar.make (view, "Поле с именем пользователя не заполнено", Snackbar.LENGTH_LONG);
            name.setBackgroundResource (R.drawable.fields_red);
            return;
        } else name.setBackgroundResource (R.drawable.fields);

        List<User> users = dbManeger.dao.getUsers ();
        for (User u : users) {
            Log.d("DB-TEST-SingUp", phNumber.getUnMasked ().toString () + " " + u.getPhNumber ().toString ());
            if (phNumber.getUnMasked ().equals (u.getPhNumber ().toString ()) ) {
                Snackbar.make (view, "Пользователь с таким номером уже зарегистрирован", Snackbar.LENGTH_LONG);
                return;
            }
        }

        Log.d("DB-TEST-SingUp", "Запись пошла");
        User newUser = new User(Integer.parseInt (phNumber.getUnMasked ()), name.getText ().toString ());
        dbManeger.dao.addUser (newUser);
        phNumber.getUnMasked (); name.getText ().toString ();
        Log.d("DB-TEST-SingUp", "Запись прошла");
        startActivity (new Intent (SingUp.this, Menu.class));
        //MainActivity.setUserValues (name.getText ().toString (), phNumber.getMasked ());
        MainActivity.setHasVisited(true);
        finishAffinity();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
    }
}
