package com.example.yulicar.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yulicar.entities.User;
import com.example.yulicar.entities.Trip;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    void addUser (User user);

    @Delete
    void removeUser (User user);

    @Update
    void updateUser (User user);

    @Query ("select * from User")
    List<User> getUsers();





    @Insert
    void addTrip(Trip trip);

    @Delete
    void deleteTrip(Trip trip);

    @Query ("select * from Trip")
    List<Trip> getTrips();





    @Insert
    void addTripUserJoin(User.TripUserJoin order);

    @Delete
    void deleteTripUserJoin(User.TripUserJoin order);

    @Query ("select * from TripUserJoin")
    List<User.TripUserJoin> getTripUserJoins();

    //TODO Написать нормальный запрос для выборки всех поездок пользователя по моб номеру
    @Query ("SELECT Trip.* From Trip\n" +
    "INNER JOIN TripUserJoin ON Trip.tripId = TripUserJoin.tripId\n" +
    "WHERE :phNumber=TripUserJoin.phNumber")
    List<Trip> selectTripsByUser (Integer phNumber);

    //TODO Написать нормальный запрос для выбки всех пассажирок по номеру поездки
    @Query ("SELECT User.* From User\n" +
            "INNER JOIN TripUserJoin ON User.phNumber = TripUserJoin.phNumber\n" +
            "WHERE :tripId=TripUserJoin.tripId")
    List<User> selectUsersByTripId (Long tripId);

}
