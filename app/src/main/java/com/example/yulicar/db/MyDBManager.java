package com.example.yulicar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDBManager {
    private Context context;
    private SQLiteDatabase db;
    public MyDBHelper myDBHelper;

    public MyDBManager(Context context) {
        this.context = context;
        myDBHelper = new MyDBHelper (context);
    }

    public void openDB(){
        db = myDBHelper.getWritableDatabase ();
    }

    public void insertToDB(String numb, String name){
        ContentValues cv = new ContentValues ();
        cv.put(MyConstants.NUMBER, numb);
        cv.put(MyConstants.USER_NAME, name);
        db.insert (MyConstants.TABLE_NAME, null, cv);
    }

    public List<String> getFromDB(){
        List<String> tempList= new ArrayList<> ();
        Cursor cursor = db.query (MyConstants.TABLE_NAME, null,
                null, null, null, null, null);
        while(cursor.moveToNext ()) {
            String number = cursor.getString (cursor.getColumnIndex (MyConstants.NUMBER));
            tempList.add (number);
        }
        cursor.close ();
        return tempList;
    }

    public void closeDB(){
        myDBHelper.close ();
    }

}
