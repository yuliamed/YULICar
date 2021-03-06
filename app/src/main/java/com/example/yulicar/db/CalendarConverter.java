package com.example.yulicar.db;

import android.util.Log;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarConverter {
    @TypeConverter
    public String fromCalendar(Calendar c) {
        Log.d("DB-TEST", c.getTime().toString());
        return
                c.getTime().toString();
    }

    @TypeConverter
    public Calendar toCalendar (String c) {
        Calendar newC = Calendar.getInstance ();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try { newC.setTime (sdf.parse (c));

        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return newC;
    }
}
