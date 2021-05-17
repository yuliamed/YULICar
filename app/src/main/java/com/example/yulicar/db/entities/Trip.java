package com.example.yulicar.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.yulicar.db.CalendarConverter;

import java.util.Calendar;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = City.class,
                        parentColumns = "cityName",
                        childColumns = "cityTo",
                        onDelete = CASCADE),
                @ForeignKey(
                        entity = City.class,
                        parentColumns = "cityName",
                        childColumns = "cityFrom",
                        onDelete = CASCADE)
        }
)
public class Trip {

    @PrimaryKey(autoGenerate = true)
    private Long tripId;
    @TypeConverters ({CalendarConverter.class})
    private Calendar date;
    private Byte numbOfSeats;
    private String carNumber;
    private String carName;
    @NonNull
    private String cityFrom;
    @NonNull
    private String cityTo;
    private boolean status = true;
    private Integer price;
    public Integer getPrice () {
        return price;
    }

    public void setPrice (Integer price) {
        this.price = price;
    }



    public boolean isStatus () {
        return status;
    }

    public void setStatus (boolean status) {
        this.status = status;
    }

    @NonNull
    public String getCityFrom () {
        return cityFrom;
    }

    @NonNull
    public String getCityTo () {
        return cityTo;
    }

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

    public Trip (Long tripId, Calendar date, Byte numbOfSeats, String carNumber, String carName, String cityFrom, String cityTo, Integer price) {
        this.tripId = tripId;
        this.date = date;
        this.numbOfSeats = numbOfSeats;
        this.carNumber = carNumber;
        this.carName = carName;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.price = price;
    }

    /*@Entity(
            tableName = "TripCityJoin",
            primaryKeys = {"cityFromId", "cityToId",  "tripId"},
            foreignKeys = {
                    @ForeignKey(
                            entity = City.class,
                            parentColumns = "cityId",
                            childColumns = "cityToId",
                            onDelete = CASCADE),
                    @ForeignKey(
                            entity = City.class,
                            parentColumns = "cityId",
                            childColumns = "cityFromId",
                            onDelete = CASCADE),
                    @ForeignKey (
                            entity = Trip.class,
                            childColumns = "tripId",
                            parentColumns = "tripId",
                            onDelete = CASCADE)})
    public static class TripCityJoin {
        @NonNull
        public final int cityFromId;
        @NonNull
        public final int cityToId;
        @NonNull public final int tripId;

        public TripCityJoin (@NonNull int cityFromId, @NonNull int cityToId, @NonNull int tripId) {
            this.cityFromId = cityFromId;
            this.cityToId = cityToId;
            this.tripId = tripId;
        }
    }*/
}
