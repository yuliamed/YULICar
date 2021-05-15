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

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.entities.Trip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import static com.example.yulicar.OneTripFragment.changeFormatOfNumbString;

public class OneTripUserFragment extends Fragment {

    private DBManeger dbManeger;
    private TextView price, direction, date, time, seats;
    private Button delete;
    private Trip order;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_one_user_trip, container, false);
        Log.d ("DB-TEST-OneUserTrip", "i'm here");
        price = (TextView) v.findViewById (R.id.tr_user_price);
        direction = (TextView) v.findViewById (R.id.tr_user_direction);
        date = (TextView) v.findViewById (R.id.tr_user_date);
        time = (TextView) v.findViewById (R.id.tr_user_time);
        seats = (TextView) v.findViewById (R.id.tr_user_price);

        delete = (Button) v.findViewById (R.id.tr_user_delete);
        int savedPhNumber = MainActivity.mSettings.getInt(MainActivity.APP_PREFERENCES_USERID, 0);

        dbManeger = new DBManeger (getContext ());
        Long tripId;

        Bundle bundle = getArguments();
        if (bundle == null) {
            return v;
        } else {
            Log.d ("--------trips","bungle not null");
            tripId = bundle.getLong ("tripId");
            order = dbManeger.dao.getTripById (tripId);
            Log.d ("--------trips",order.getCarName ());
        }
        price.setText (String.valueOf (order.getPrice ()) + "BYR");
        direction.setText (order.getCityFrom () + " - " + order.getCityTo ());
        String hourS = changeFormatOfNumbString (order.getDate ().get(Calendar.HOUR_OF_DAY));
        String minuteS = changeFormatOfNumbString (order.getDate ().get (Calendar.MINUTE));
        String day = changeFormatOfNumbString (order.getDate ().get (Calendar.DAY_OF_MONTH));
        String month = changeFormatOfNumbString(order.getDate ().get (Calendar.MONTH));
        String year = String.valueOf (order.getDate ().get (Calendar.YEAR));
        date.setText (day + "." + month + "." + year);
        time.setText (hourS+ ":" + minuteS);
//        seats.setText (order.getNumbOfSeats ());
        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Snackbar.make (v,
                        "Скоро всё появится))",
                        Snackbar.LENGTH_LONG).show ();
            }
        });
        return v;
    }


}