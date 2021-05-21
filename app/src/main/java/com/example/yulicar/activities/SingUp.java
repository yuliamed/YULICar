package com.example.yulicar.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.yulicar.R;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.User;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SingUp extends Activity {
    DBManeger dbManeger;
    private MaskedEditText phNumber;
    private EditText name;
    boolean isDriver = false;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.sign_up);
        name = (EditText) findViewById (R.id.etName);
        phNumber = findViewById (R.id.etPhone);
    }

    @Override
    protected void onStart () {
        super.onStart ();
    }

    public void check(View view){
        CheckBox checkBox = (CheckBox) findViewById (R.id.check_is_driver);
        if (checkBox.isChecked ()) {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.dialog_fragment, null);
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
            mDialogBuilder.setView(promptsView);
            final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);
            //Настраиваем сообщение в диалоговом окне:
            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    //Вводим текст и отображаем в строке ввода на основном экране:
                                    if (userInput.getText ().toString ().equals ("heart")) {
                                        isDriver = true;
                                        Log.d ("isDriver", "ДА ЭТО ЖЕ ВОДИТЕЛЬ");
                                    }
                                    else {
                                        return;
                                    }
                                }
                            })
                    .setNegativeButton("Отмена",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });
            //Создаем AlertDialog:
            AlertDialog alertDialog = mDialogBuilder.create();
            //и отображаем его:
            alertDialog.show();
        }
    }

    public void signUp (View view) {
        Log.d("DB-TEST-SingUp", "Start SignUp");

        if (phNumber.getUnmaskedText ().length () == 0 || phNumber.getUnmaskedText ().length () < 9){
            Log.d("DB-TEST-SingUp", "wrong phNumber");
            phNumber.setBackgroundResource (R.drawable.fields_red);
            Snackbar.make (view, "Поле номера телефона не заполнено", Snackbar.LENGTH_LONG);
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
            Log.d("DB-TEST-SingUp", phNumber.getUnmaskedText ().toString () + " " + u.getPhNumber ().toString ());
            if (phNumber.getUnmaskedText ().equals (u.getPhNumber ().toString ()) ) {
                Snackbar.make (view, "Пользователь с таким номером уже зарегистрирован", Snackbar.LENGTH_LONG);
                return;
            }
        }

        Log.d("DB-TEST-SingUp", "Запись пошла");
        User newUser = new User(Integer.parseInt (phNumber.getUnmaskedText ()), name.getText ().toString (), isDriver);
        dbManeger.dao.addUser (newUser);
        phNumber.getUnmaskedText (); name.getText ().toString ();
        Log.d("DB-TEST-SingUp", "Запись прошла");
        if (!isDriver) {
            startActivity (new Intent (SingUp.this, Menu.class));
            MainActivity.setHasVisited(true);
            MainActivity.setUserValues (newUser.getPhNumber ());
            finishAffinity();
        } else {
            startActivity (new Intent (SingUp.this, DriverActivity.class));
            finishAffinity();
        }

    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
    }
}
