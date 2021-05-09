package com.example.yulicar.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.yulicar.converters.CalendarConverter;

import java.util.Calendar;

@Entity
public class Trip {

    @PrimaryKey(autoGenerate = true)
    private Long tripId;
    @TypeConverters ({CalendarConverter.class})
    private Calendar date;
    private Byte numbOfSeats;
    private String cityFrom;
    private String cityTo;

    public String getCityFrom () {
        return cityFrom;
    }

    public String getCityTo () {
        return cityTo;
    }
    private String carNumber;
    private String carName;

    public Long getTripId () {
        return tripId;
    }

    public Calendar getDate () {
        return date;
    }

    public Byte getNumbOfSeats () {
        return numbOfSeats;
    }

    public String getCarNumber () {
        return carNumber;
    }

    public String getCarName () {
        return carName;
    }

    public Trip (Long tripId, Calendar date, Byte numbOfSeats, String cityFrom, String cityTo, String carNumber, String carName) {
        this.tripId = tripId;
        this.date = date;
        this.numbOfSeats = numbOfSeats;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.carNumber = carNumber;
        this.carName = carName;
    }
}
