package com.example.yulicar.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity (
        foreignKeys = {
                @ForeignKey(
                        entity = City.class,
                        parentColumns = "cityName",
                        childColumns = "city",
                        onDelete = CASCADE)}
)
public class Location {

    @PrimaryKey(autoGenerate = true)
    private Integer locationId;
    private String location;
    private String city;

    public void setLocation (String location) {
        this.location = location;
    }
    public String getLocation () {
        return location;
    }
    public Integer getLocationId () {
        return locationId;
    }
    public String getCity () {
        return city;
    }
    public void setCity (String city) {
        this.city = city;
    }

    public Location (Integer locationId, String location, String city) {
        this.locationId = locationId;
        this.location = location;
        this.city = city;
    }
}
