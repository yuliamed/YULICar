package com.example.yulicar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.yulicar.db.MyDBManager;

public class SingUp extends Activity {
    private MyDBManager myDBManager;
    private EditText phNumber;
    private EditText name;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_up);
        myDBManager = new MyDBManager (this);
        name = (EditText) findViewById (R.id.etName);
        phNumber = findViewById (R.id.etPhone);

    }

    /*@Override
    protected void onResume () {
        super.onResume ();

    }*/

    @Override
    protected void onStart () {
        super.onStart ();
        myDBManager.openDB ();
        /*for (String numb : myDBManager.getFromDB ()) {
            System.out.println (numb + "\n");
        }*/
    }

    public void signUp (View view) {
        myDBManager.insertToDB (phNumber.getText ().toString (), name.getText ().toString ());
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        myDBManager.closeDB ();
    }
}
