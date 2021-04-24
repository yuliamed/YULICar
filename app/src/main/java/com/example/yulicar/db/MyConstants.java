package com.example.yulicar.db;

public class MyConstants {
    public static final String TABLE_NAME = "user";
    public static final String _ID = "_id";
    public static final String NUMBER = "number";
    public static final String USER_NAME = "user_name";
    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + _ID +
            " INTEGER PRIMARY KEY, " + NUMBER + " TEXT, "+ USER_NAME+ " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
