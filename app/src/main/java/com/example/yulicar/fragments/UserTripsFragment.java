package com.example.yulicar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yulicar.R;
import com.example.yulicar.activities.MainActivity;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.Trip;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class UserTripsFragment extends Fragment {
    private DBManeger dbManeger;
    private List<Trip> orders;
    private Button activeTrips, histTrips;
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_user_trips, container, false);
        activeTrips = (Button) v.findViewById (R.id.activ_trip);
        activeTrips.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Snackbar.make (v, "Скоро всё будет", Snackbar.LENGTH_LONG);
            }
        });
        histTrips = (Button)v.findViewById (R.id.history_trips);
        histTrips.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Snackbar.make (v, "Скоро всё будет", Snackbar.LENGTH_LONG);
            }
        });
        int savedPhNumber = MainActivity.mSettings.getInt(MainActivity.APP_PREFERENCES_USERID, 0);
        dbManeger = new DBManeger (getContext ());
        orders = dbManeger.dao.selectTripsByUser (savedPhNumber);
        FragmentManager manager = getFragmentManager ();
        TextView noo = v.findViewById (R.id.noo);
        if (orders.isEmpty ()){
            noo.setText ("Поездок нет!");
            return v;
        } else {
            for (int i = 0; i <orders.size (); i++) {
                noo.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = manager.beginTransaction ();
                OneTripUserFragment order = new OneTripUserFragment ();
                Bundle bundleInnerFragment = new Bundle ();
                bundleInnerFragment.putLong ("tripId", orders.get (i).getTripId ());
                order.setArguments (bundleInnerFragment);
                fragmentTransaction.add (R.id.user_container, order);
                fragmentTransaction.commit ();
            }
        }
        return v;
    }
}
