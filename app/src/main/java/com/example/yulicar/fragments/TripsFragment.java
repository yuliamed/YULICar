package com.example.yulicar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yulicar.R;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.Trip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TripsFragment extends Fragment {
    private TextView directionInfo;
    private TextView dateInfo;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private DBManeger dbManeger;

    public TripsFragment () {
    }

    @Override
    public void onStop () {
        super.onStop ();
    }

    @Override
    public void onPause () {
        super.onPause ();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_trips, container, false);
        dateInfo = (TextView) v.findViewById (R.id.dateOfTrip);
        directionInfo = (TextView) v.findViewById (R.id.directionOfTrip);
        dbManeger = new DBManeger (getContext ());
        String cityFrom = "";
        String cityTo = "";
        Integer day = 0, month = 0, year = 0;
        Bundle bundle = getArguments();
        if (bundle != null) {
            cityFrom = bundle.getString ("cityFrom");
            cityTo = bundle.getString ("cityTo");
            day = bundle.getInt ("day");
            month = bundle.getInt ("month");
            year = bundle.getInt ("year");
        }

        directionInfo.setText (cityFrom + " - " + cityTo);
        dateInfo.setText (OneTripFragment.changeFormatOfNumbString (day) + "."
                + OneTripFragment.changeFormatOfNumbString (month+1)+ "." + String.valueOf (year));


        List<Trip> tripsByCities =  dbManeger.dao.selectTripsByCities (cityFrom, cityTo);
        for(Trip t : tripsByCities) {
            Log.d("DB-TEST-all_trips", t.getTripId () + " : " + t.getCityFrom () +  " : "
                    +t.getCityTo ()+" : "+t.getDate ().get(Calendar.DAY_OF_MONTH) + " : "
                    + t.getDate ().get (Calendar.MONTH) + " : "
                    + t.getDate ().get (Calendar.YEAR));

        }
        List<Trip> trips = new ArrayList<> ();
        for (Trip t : tripsByCities){
            if(month == (t.getDate ().get(Calendar.MONTH))
                    && day == (t.getDate ().get(Calendar.DAY_OF_MONTH))
                    && year == (t.getDate ().get(Calendar.YEAR) ) && t.getNumbOfSeats () > 0) {
                trips.add(t);
            }
        }
        manager = getFragmentManager ();
        if (trips.isEmpty ()) {
            directionInfo.setText (cityFrom + " - " + cityTo + " - ?????????????? ??????");
            dateInfo.setText (OneTripFragment.changeFormatOfNumbString (day) + "."
                    + OneTripFragment.changeFormatOfNumbString (month+1)+ "." + String.valueOf (year));
        } else {
            for (Trip t : trips) {
                FragmentTransaction fragmentTransaction = manager.beginTransaction ();
                OneTripFragment oneTripFragment = new OneTripFragment ();
                Bundle bundleInnerFragment = new Bundle ();
                bundleInnerFragment.putLong ("tripId", t.getTripId ());
                oneTripFragment.setArguments (bundleInnerFragment);
                fragmentTransaction.add (R.id.container, oneTripFragment);
                fragmentTransaction.commit ();
            }
        }
        return v;
    }
}
