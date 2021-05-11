package com.example.yulicar.entities;

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

    public int getMiles () {
        return miles;
    }

    public void setMiles (int miles) {
        this.miles = miles;
    }

    public User (@NonNull Integer phNumber, String name) {
        this.phNumber = phNumber;
        this.name = name;
    }

    @NonNull
    public Integer getPhNumber () {
        return phNumber;
    }

    public String getName() {
        return name;
    }

    @Entity(
            tableName = "TripUserJoin",
            primaryKeys = {"phNumber", "tripId"},
            foreignKeys = {
                    @ForeignKey (
                            entity = User.class,
                            parentColumns = "phNumber",
                            childColumns = "phNumber",
                            onDelete = CASCADE),
                    @ForeignKey (
                            entity = Trip.class,
                            childColumns = "tripId",
                            parentColumns = "tripId",
                            onDelete = CASCADE)})
    public static class TripUserJoin {
        @NonNull public final Integer phNumber;
        @NonNull public final Long tripId;

        public TripUserJoin (@NonNull Integer phNumber, @NonNull Long tripId) {
            this.phNumber = phNumber;
            this.tripId = tripId;
        }
    }
}
