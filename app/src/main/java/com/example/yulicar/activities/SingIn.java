package com.example.yulicar.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.yulicar.R;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.User;
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
        mSettings = getSharedPreferences (MainActivity.APP_PREFERENCES_VISITED, Context.MODE_PRIVATE);
        super.onStart ();
    }

    private boolean isDriver = false;

    public void checkDriver () {
        LayoutInflater li = LayoutInflater.from (this);
        View promptsView = li.inflate (R.layout.dialog_fragment, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder (this);
        mDialogBuilder.setView (promptsView);
        final EditText userInput = (EditText) promptsView.findViewById (R.id.input_text);
        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable (false)
                .setPositiveButton ("OK",
                        new DialogInterface.OnClickListener () {
                            public void onClick (DialogInterface dialog, int id) {
                                //Вводим текст и отображаем в строке ввода на основном экране:
                                if (userInput.getText ().toString ().equals ("heart")) {
                                    Log.d ("isDriver", "ДА ЭТО ЖЕ ВОДИТЕЛЬ");
                                    isDriver = true;
                                } else {
                                    isDriver = false;
                                    return;
                                }
                            }
                        })
                .setNegativeButton ("Отмена",
                        new DialogInterface.OnClickListener () {
                            public void onClick (DialogInterface dialog, int id) {
                                dialog.cancel ();
                            }
                        });
        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create ();
        //и отображаем его:
        alertDialog.show ();
    }

    //обработчик кнопки на Activity
    public void signIn (View view) {
        List<User> users = dbManeger.dao.getUsers ();
        for (User u : users) {
            if (phNumber.getUnmaskedText ().equals (u.getPhNumber ().toString ())) {
                if (u.getDriver ()) {
                    checkDriver ();
                    if (isDriver) {
                        startActivity (new Intent (SingIn.this, DriverActivity.class));
                        finishAffinity ();
                    } else {
                        Snackbar.make (view, "Шпиён ты какой-то, раз пароль не знаешь",Snackbar.LENGTH_LONG );
                        return;
                    }

                } else {
                    startActivity (new Intent (SingIn.this, Menu.class));
                    MainActivity.setUserValues (u.getPhNumber ());
                    MainActivity.setHasVisited (true);
                    finishAffinity ();
                    return;
                }
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

















