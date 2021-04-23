package com.example.yulicar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.yulicar.db.MyDBManager;

public class SingIn extends Activity {
    private MyDBManager myDBManager;
    private EditText phNumber;
    private TextView test;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_in);
        myDBManager = new MyDBManager (this);
        phNumber = findViewById (R.id.etPhone);
        test = (TextView) findViewById (R.id.test);
    }

    public void signIn (View view) {
        myDBManager.openDB ();
        for ( String numb : myDBManager.getFromDB ()){
            test.append (numb);
            test.append ("\n");
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        myDBManager.closeDB ();
    }
}

















