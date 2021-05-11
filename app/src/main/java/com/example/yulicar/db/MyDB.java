package com.example.yulicar.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.yulicar.entities.City;
import com.example.yulicar.entities.Trip;
import com.example.yulicar.entities.User;

@Database (entities = {User.class, Trip.class, User.TripUserJoin.class, City.class}, version =1)
public abstract class MyDB extends RoomDatabase {
    public abstract MyDao getMyDao();
}
