package com.example.yulicar.fragments;

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

import com.example.yulicar.activities.MainActivity;
import com.example.yulicar.R;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.Trip;
import com.example.yulicar.db.entities.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

import static com.example.yulicar.fragments.OneTripFragment.changeFormatOfNumbString;

public class OneTripUserFragment extends Fragment {

    private static boolean clicked = false;
    private DBManeger dbManeger;
    private TextView price, direction, date, time, seats;
    private Button delete;
    private Trip order;
    //public boolean clicked = false;
    private int saved_seats = 0;
    private int phNumber;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_one_user_trip, container, false);
        Log.d ("DB-TEST-OneUserTrip", "i'm here");
        price = (TextView) v.findViewById (R.id.tr_user_price);
        direction = (TextView) v.findViewById (R.id.tr_user_direction);
        date = (TextView) v.findViewById (R.id.tr_user_date);
        time = (TextView) v.findViewById (R.id.tr_user_time);
        seats = (TextView) v.findViewById (R.id.tr_user_numbOfSeats);

        delete = (Button) v.findViewById (R.id.tr_user_delete);
        int savedPhNumber = MainActivity.mSettings.getInt (MainActivity.APP_PREFERENCES_USERID, 0);

        dbManeger = new DBManeger (getContext ());
        Long tripId;

        Bundle bundle = getArguments ();
        if (bundle == null) {
            return v;
        } else {
            Log.d ("--------trips", "bungle not null");
            tripId = bundle.getLong ("tripId");
            order = dbManeger.dao.getTripById (tripId);
            Log.d ("--------trips", order.getCarName ());
        }

        phNumber = MainActivity.mSettings.getInt (MainActivity.APP_PREFERENCES_USERID, 0);
        List<User.TripUserJoin> orders = dbManeger.dao.getTripUserJoins ();
        for (User.TripUserJoin o : orders) {
            if (o.tripId == tripId && o.phNumber == phNumber) {
                saved_seats = o.getNumbOfSeats ();
                Log.d ("НАЙДЕНООООО", String.valueOf (seats));
                break;
            }
        }
        price.setText (String.valueOf (order.getPrice ()) + "BYR");
        direction.setText (order.getCityFrom () + " - " + order.getCityTo ());
        String hourS = changeFormatOfNumbString (order.getDate ().get (Calendar.HOUR_OF_DAY));
        String minuteS = changeFormatOfNumbString (order.getDate ().get (Calendar.MINUTE));
        String day = changeFormatOfNumbString (order.getDate ().get (Calendar.DAY_OF_MONTH));
        String month = changeFormatOfNumbString (order.getDate ().get (Calendar.MONTH));
        String year = String.valueOf (order.getDate ().get (Calendar.YEAR));
        date.setText (day + "." + month + "." + year);
        time.setText (hourS + ":" + minuteS);
        seats.setText (saved_seats + " мест");

        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Snackbar.make (v,
                        "Скоро всё появится))",
                        Snackbar.LENGTH_LONG).show ();
                List<Trip> all_trips = dbManeger.dao.selectTripsByUser (phNumber);
                for (Trip t : all_trips) Log.d ("Delete------All", String.valueOf (t.getTripId ()));
                Log.d ("Delete------Selected", String.valueOf (tripId));


                dbManeger.dao.deleteOrderByTripIdUserNumb ((phNumber), tripId);
                dbManeger.dao.updateNumbOfSeats (-saved_seats, tripId);
                all_trips = dbManeger.dao.selectTripsByUser (phNumber);
                for (Trip t : all_trips)
                    Log.d ("Delete------deleted", String.valueOf (t.getTripId ()));
                OneTripUserFragment.clicked = true;
                getActivity ().getSupportFragmentManager ().beginTransaction ().remove (OneTripUserFragment.this).commit ();
            }
        });
        Log.d ("Delete------clicked", String.valueOf (clicked));
        return v;
    }


}
