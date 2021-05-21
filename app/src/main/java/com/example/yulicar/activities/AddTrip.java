package com.example.yulicar.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yulicar.R;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.City;
import com.example.yulicar.db.entities.Trip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AddTrip extends Activity {

    private Spinner cityFrom, cityTo, hour, minutes;
    private CalendarView calendar;
    private EditText car, carNumber, price, numbOfSeats;
    private DBManeger dbManeger;
    private int selectedDay, selectedMonth, selectedYear;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        setContentView (R.layout.new_trip_activity);

        dbManeger = new DBManeger (this);
        cityFrom = (Spinner) findViewById (R.id.dr_sp_CityFrom);
        cityTo = (Spinner) findViewById (R.id.dr_sp_CityTo);
        fillInTowns ();
        hour = (Spinner) findViewById (R.id.dr_sp_hour);
        fillInHours ();
        minutes = (Spinner) findViewById (R.id.dr_sp_min);
        fillInMinutes ();
        calendar = (CalendarView) findViewById (R.id.dr_calendar);
        calendar.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange (@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDay = dayOfMonth - 1;
                selectedMonth = month;
                selectedYear = year;
            }
        });

        car = (EditText) findViewById (R.id.dr_car_name);
        carNumber = (EditText) findViewById (R.id.dr_car_numb);
        price = (EditText) findViewById (R.id.dr_price);
        numbOfSeats = (EditText) findViewById (R.id.dr_numb_of_seats);
        super.onCreate (savedInstanceState);
    }

    public void addTrip(View v) {
        Log.d ("AddTrip", "Запись пошла");
        if (checkFields(v)) {
            String selCityFrom = cityFrom.getSelectedItem ().toString ();
            String selCityTo = cityTo.getSelectedItem ().toString ();
            int selHour = Integer.parseInt (hour.getSelectedItem ().toString ());
            int selMin = Integer.parseInt (minutes.getSelectedItem ().toString ());

            Calendar calendar = new GregorianCalendar ();
            calendar.set(Calendar.YEAR, selectedYear);
            calendar.set(Calendar.MONTH, selectedMonth);
            calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
            calendar.set(Calendar.HOUR_OF_DAY, selHour);
            calendar.set(Calendar.MINUTE, selMin);
            Log.d("AddTrip", "чпойна "+ hour.getSelectedItem ().toString () + " : " +
                    String.valueOf (selHour) + " : " + calendar.get (Calendar.HOUR_OF_DAY));
            byte seats = Byte.parseByte (numbOfSeats.getText ().toString ());
            String carNumb = carNumber.getText ().toString ();
            String carName = car.getText ().toString ();
            int selPrice = Integer.parseInt (price.getText ().toString ());
            Trip trip1 = new Trip(null, calendar, seats, carNumb,
                    carName,selCityFrom, selCityTo, selPrice);
            dbManeger.dao.addTrip (trip1);
            Log.d ("AddTrip", "Запись прошла");
            this.finish ();
        }
    }
    private boolean checkFields(View v) {
        if(cityFrom.getSelectedItem ().equals (cityTo.getSelectedItem ())) {
            Snackbar.make (v, "Куда ехать то? Задана поездка в свой же город", Snackbar.LENGTH_LONG).show ();
            cityFrom.setBackgroundResource (R.drawable.fields_red);
            cityTo.setBackgroundResource (R.drawable.fields_red);
            return false;
        }
        if (car.getText ().length () == 0 ) {
            car.setBackgroundResource (R.drawable.fields_red);
            Snackbar.make (v, "Информация про машинку не введена", Snackbar.LENGTH_LONG).show ();
            return false;
        }
        if(carNumber.getText ().length ()==0){
            carNumber.setBackgroundResource (R.drawable.fields_red);
            Snackbar.make (v, "Информация про номер машинки не введена", Snackbar.LENGTH_LONG).show ();
            return false;
        }
        if (price.getText ().length () == 0) {
            price.setBackgroundResource (R.drawable.fields_red);
            Snackbar.make (v, "Информация про цену не введена", Snackbar.LENGTH_LONG).show ();
            return false;
        }
        if (numbOfSeats.getText ().length () == 0) {
            numbOfSeats.setBackgroundResource (R.drawable.fields_red);
            Snackbar.make (v, "Информация про количество мест в машинке не введена", Snackbar.LENGTH_LONG).show ();
            return false;
        }
        cityFrom.setBackgroundResource (R.drawable.spinner_shape);
        cityTo.setBackgroundResource (R.drawable.spinner_shape);
        car.setBackgroundResource (R.drawable.fields);
        carNumber.setBackgroundResource (R.drawable.fields);
        price.setBackgroundResource (R.drawable.fields);
        numbOfSeats.setBackgroundResource (R.drawable.fields);
        return true;
    }
    private void fillInMinutes () {
        int minStart = 0;
        int minEnd = 60;
        String[] strMinutes = new String[7];
        for (int i = 0; i < strMinutes.length; i++) {
            strMinutes[i] = String.valueOf (minStart + i * 10);
            Log.d ("HOUR", strMinutes[i]);
        }
        ArrayAdapter<String> adapterMin = new ArrayAdapter<String> (this, R.layout.row_for_spinner, R.id.row_of_spinner, strMinutes);
        minutes.setAdapter (adapterMin);
    }

    private void fillInHours () {
        int hourStart = 5;
        int hourEnd = 23;
        String[] hours = new String[hourEnd - hourStart + 1];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = String.valueOf (hourStart + i);
            Log.d ("HOUR", hours[i]);
        }
        ArrayAdapter<String> adapterHour = new ArrayAdapter<String> (this, R.layout.row_for_spinner, R.id.row_of_spinner, hours);
        hour.setAdapter (adapterHour);
    }

    private void fillInTowns () {
        List<City> cities = dbManeger.dao.getCities ();
        String[] citiesFrom_To = new String[cities.size ()];
        for (int i = 0; i < cities.size (); i++) {
            citiesFrom_To[i] = cities.get (i).getCityName ();
            Log.d ("DB-Text_city", cities.get (i).getCityName ());
        }
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String> (this, R.layout.row_for_spinner, R.id.row_of_spinner, citiesFrom_To);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<String> (this, R.layout.row_for_spinner, R.id.row_of_spinner, citiesFrom_To);
        cityFrom.setAdapter (adapterFrom);
        cityTo.setAdapter (adapterTo);
    }
}
