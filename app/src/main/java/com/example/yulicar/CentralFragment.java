package com.example.yulicar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.MyDB;
import com.example.yulicar.db.MyDao;
import com.example.yulicar.entities.City;
import com.example.yulicar.entities.Trip;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //MyDB db;
    //MyDao dao;
    DBManeger dbManeger;
    List<Trip> trips;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CentralFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        dbManeger.dao.addCity (city2);
        City city3 = new City("Gomel");
        dbManeger.dao.addCity (city3);
        Calendar calendar = new GregorianCalendar ();
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, 17);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 00);
        Trip trip1 = new Trip(null, calendar, (byte) 15, "9023-AB-2", "Mersedes","Gomel", "Minsk");
        dbManeger.dao.addTrip (trip1);*/
        trips = dbManeger.dao.getTrips();
        for (Trip t : trips) {
            Log.d("DB-TEST", t.getTripId () + " : " + t.getCityFrom () +  " : " +t.getCityTo ()+" : "
                    + t.getDate ().get (Calendar.HOUR));
        }
    }

    @Override
    public void onResume () {
        super.onResume ();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_search, null);
        Button signOff = (Button) view.findViewById (R.id.search);
        signOff.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                TripsFragment tripsFragment = new TripsFragment ();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager ();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace (R.id.fragment_conteiner, tripsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Spinner cityFrom = (Spinner) view.findViewById (R.id.spinnerCityFrom);
        String[] citiesFrom = new String[trips.size ()];
        Spinner cityTo = (Spinner) view.findViewById (R.id.spinnerCityTo);
        String[] citiesTo = new String[trips.size ()];
        for (int i = 0; i < trips.size (); i++) {
            citiesFrom[i] = trips.get(i).getCityFrom ();
            citiesTo[i] = trips.get(i).getCityTo();
        }
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, citiesFrom);
        ArrayAdapter<String> adapterTo = new ArrayAdapter<String> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, citiesTo);
        cityFrom.setAdapter(adapterFrom);
        cityTo.setAdapter(adapterTo);

        cityFrom.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                //String[] choose = getResources().getStringArray(R.array.animals);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

}