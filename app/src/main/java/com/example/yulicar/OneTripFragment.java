package com.example.yulicar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.entities.Trip;

import java.util.Calendar;


public class OneTripFragment extends Fragment {
    TextView time, car,carNumber,numbOfSeats;
    Button priceBook;
    private DBManeger dbManeger;


    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_one_trip, container, false);
        Log.d ("DB-TEST-OneTrip", "i'm here");
        dbManeger = new DBManeger (getContext ());
        Long tripId;
        Trip trip;
        time = (TextView) v.findViewById (R.id.time);
        car = (TextView) v.findViewById (R.id.car);
        carNumber = (TextView) v.findViewById (R.id.carNumber);
        numbOfSeats = (TextView) v.findViewById (R.id.numbOfSeats);

        priceBook = (Button) v.findViewById (R.id.price_book);
        String timeS, carS,carNumberS,numbOfSeatsS, priceS;
        Bundle bundle = getArguments();
        if (bundle == null) {
            return v;
        } else {
            tripId = bundle.getLong ("tripId");
            trip = dbManeger.dao.getTripById (tripId);
        }
        String hourS = changeFormatOfNumbString (trip.getDate ().get(Calendar.HOUR_OF_DAY));
        String minuteS = changeFormatOfNumbString (trip.getDate ().get (Calendar.MINUTE));
        time.setText (hourS + ":" + minuteS);
        car.setText (trip.getCarName ());
        carNumber.setText (trip.getCarNumber ());
        numbOfSeats.setText (trip.getNumbOfSeats () + " мест");
        priceBook.setText (trip.getPrice () + " BYR");


        priceBook.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                BookTripFragment bookTripFragment = new BookTripFragment ();
                Bundle bundle = new Bundle ();
                bundle.putLong("tripId", tripId/*info from spinner*/);
                Log.d("DB-TEST-selectedTrip", String.valueOf (tripId) );
                bookTripFragment.setArguments (bundle);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager ();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace (R.id.fragment_conteiner, bookTripFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return v;
    }

    public static String changeFormatOfNumbString(int value) {
        if (value < 10) {
            return "0" + String.valueOf (value);
        } else return String.valueOf (value);
    }
}
