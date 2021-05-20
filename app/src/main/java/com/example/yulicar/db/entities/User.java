package com.example.yulicar.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private Integer phNumber;
    private String name;
    private int miles = 0;
    private Boolean isDriver = false;

    public Boolean getDriver () {
        return isDriver;
    }

    public void setDriver (Boolean driver) {
        isDriver = driver;
    }

    public int getMiles () {
        return miles;
    }

    public void setMiles (int miles) {
        this.miles = miles;
    }

    public User (@NonNull Integer phNumber, String name, boolean isDriver) {
        this.phNumber = phNumber;
        this.name = name;
        this.isDriver = isDriver;
    }

    @NonNull
    public Integer getPhNumber () {
        return phNumber;
    }

    public String getName () {
        return name;
    }

    @Entity(
            tableName = "TripUserJoin",
            primaryKeys = {"phNumber", "tripId"},
            foreignKeys = {
                    @ForeignKey(
                            entity = User.class,
                            parentColumns = "phNumber",
                            childColumns = "phNumber",
                            onDelete = CASCADE),
                    @ForeignKey(
                            entity = Trip.class,
                            childColumns = "tripId",
                            parentColumns = "tripId",
                            onDelete = CASCADE),
                    @ForeignKey(
                            entity = Location.class,
                            parentColumns = "locationId",
                            childColumns = "locationId",
                            onDelete = CASCADE)
                    })
    public static class TripUserJoin {
        @NonNull
        public final Integer phNumber;
        @NonNull
        public final Long tripId;
        @NonNull
        public final Integer locationId;

        public int getNumbOfSeats () {
            return numbOfSeats;
        }

        private int numbOfSeats;

        public TripUserJoin (@NonNull Integer phNumber, @NonNull Long tripId,
                             @NonNull Integer locationId, int numbOfSeats) {
            this.phNumber = phNumber;
            this.tripId = tripId;
            this.locationId = locationId;
            this.numbOfSeats = numbOfSeats;
        }
    }
}
