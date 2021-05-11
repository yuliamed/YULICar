package com.example.yulicar.db;

import androidx.room.Room;

public class DBManeger {
    public static MyDB db;
    public static MyDao dao;

    public void openDB()
    {

    }

    public DBManeger (android.content.Context context) {
        db = Room.databaseBuilder (context, MyDB.class, "db1").allowMainThreadQueries ().build ();
        dao = db.getMyDao ();
    }
}
