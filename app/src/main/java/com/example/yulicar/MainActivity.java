package com.example.yulicar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bSignIn;
    private Button bSignUp;
    private Button bMenuTest;
    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

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
}