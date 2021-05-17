package com.example.yulicar.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class City {
    @PrimaryKey @NonNull
    private String cityName;

    public String getCityName () {
        return cityName;
    }

    public void setCityName (String cityName) {
        this.cityName = cityName;
    }

    public City (String cityName) {
        this.cityName = cityName;
    }
}
