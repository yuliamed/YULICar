package com.example.yulicar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.MyDB;
import com.example.yulicar.db.MyDao;
import com.example.yulicar.entities.City;
import com.example.yulicar.entities.Trip;
import com.example.yulicar.entities.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CentralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CentralFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private DBManeger dbManeger;
    private List<Trip> trips;
    private Spinner cityFrom;
    private Spinner cityTo;
    private CalendarView calendarView;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDay;
    private Bundle bundle;
    private TripsFragment tripsFragment;
    private String selectedCityTo;
    private String selectedCityFrom;



    public static CentralFragment newInstance (String param1, String param2) {
        CentralFragment fragment = new CentralFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onStart () {
        super.onStart ();
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        dbManeger = new DBManeger (getContext ());


        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }
        //db = Room.databaseBuilder (getActivity ().getApplicationContext (), MyDB.class, "db").allowMainThreadQueries ().build ();
        //dao = db.getMyDao ();
        /*City city1 = new City("Mogilev");
        dbManeger.dao.addCity (city1);
        City city2 = new City("Minsk");
        dbManeger.dao.addCity (city2);*/
        /*City city3 = new City("Gomel");
        dbManeger.dao.addCity (city3);*/

        /*Calendar calendar = new GregorianCalendar ();
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        calendar.set(Calendar.HOUR, 19);
        calendar.set(Calendar.MINUTE, 00);
        Trip trip1 = new Trip(null, calendar, (byte) 15, "9023-AB-2", "Mersedes","Mogilev", "Minsk", 15);
        dbManeger.dao.addTrip (trip1);
        Trip trip2 = new Trip(null, calendar, (byte) 15, "9025-AL-6", "Mersedes","Mogilev", "Minsk", 13);
        dbManeger.dao.addTrip (trip2);
        Trip trip3 = new Trip(null, calendar, (byte) 15, "1111-90-7", "Mersedes","Minsk", "Mogilev", 13);
        dbManeger.dao.addTrip (trip3);*/
        trips = dbManeger.dao.getTrips();
        for (Trip t : trips) {
            Log.d("DB-TEST-central", t.getTripId () + " : " + t.getCityFrom () +  " : " +t.getCityTo ()+" : "
                    + t.getDate ().get (Calendar.DAY_OF_MONTH) + " : " + t.getDate ().get (Calendar.HOUR_OF_DAY)
                    + " : " + t.getDate ().get (Calendar.HOUR) + " + " + t.getNumbOfSeats ());
        }
        List<User.TripUserJoin> orders = dbManeger.dao.getTripUserJoins ();
        for (User.TripUserJoin t : orders){
            Log.d("DB-TEST-central", "phNumber: " + t.phNumber + " TripId: " + t.tripId);
        }
    }

    @Override
    public void onResume () {
        super.onResume ();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        //todo - удали потом нафиг
        int savedNumber = MainActivity.mSettings.getInt (MainActivity.APP_PREFERENCES_USERID, 0);
        Log.d("ЗАДОЛБАЛО", String.valueOf (savedNumber));


        View view = inflater.inflate (R.layout.fragment_search, null);
        cityFrom = (Spinner) view.findViewById (R.id.spinnerCityFrom);
        cityTo = (Spinner) view.findViewById (R.id.spinnerCityTo);
        fillInTowns(view);
        calendarView = (CalendarView) view.findViewById (R.id.calendarView);
        calendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange (@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDay= dayOfMonth;
                selectedMonth = month;
                selectedYear = year;
            }
        });

        Button search = (Button) view.findViewById (R.id.search);
        search.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                tripsFragment = new TripsFragment ();
                selectedCityTo = cityTo.getSelectedItem ().toString ();
                selectedCityFrom = cityFrom.getSelectedItem ().toString ();
                bundle = new Bundle ();
                if (selectedCityFrom.equals (selectedCityTo)) {
                    cityFrom.setBackgroundResource (R.drawable.fields_red);
                    cityTo.setBackgroundResource (R.drawable.fields_red);
                    Snackbar.make (view,
                            "Вам не надо никуда ехать, вы уже на месте назначения :D",
                            Snackbar.LENGTH_LONG).show ();
                } else {
                    cityFrom.setBackgroundResource (R.drawable.fields);
                    cityTo.setBackgroundResource (R.drawable.fields);
                    fillValuesForNextFragment();
                }
            }
        });
        return view;
    }

    private void fillInTowns (View view) {
        List<City> cities = dbManeger.dao.getCities ();
        String[] citiesFrom_To = new String[cities.size ()];
        for (int i = 0; i < cities.size (); i++) {
            citiesFrom_To[i] = cities.get(i).getCityName();
            Log.d ("DB-Text_city", cities.get(i).getCityName());
        }
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, citiesFrom_To);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<String> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, citiesFrom_To);
        cityFrom.setAdapter(adapterFrom);
        cityTo.setAdapter(adapterTo);
    }

    private void fillValuesForNextFragment() {
        bundle.putString("cityFrom", selectedCityFrom/*info from spinner*/);
        bundle.putString("cityTo", selectedCityTo/*info from spinner*/);
        bundle.putInt ("day", selectedDay);
        bundle.putInt ("month", selectedMonth);
        bundle.putInt ("year", selectedYear);
        Log.d ("DB-TEST-selected", selectedCityFrom + " " + selectedCityTo + " " + String.valueOf (selectedDay) + " "
                + String.valueOf (selectedMonth) + " " + String.valueOf (selectedYear));
        tripsFragment.setArguments (bundle);
        FragmentManager fragmentManager = getActivity ().getSupportFragmentManager ();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
        fragmentTransaction.replace (R.id.fragment_conteiner, tripsFragment);
        fragmentTransaction.addToBackStack (null);
        fragmentTransaction.commit ();
    }
}