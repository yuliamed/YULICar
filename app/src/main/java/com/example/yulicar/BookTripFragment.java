package com.example.yulicar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.style.MaskFilterSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.MyDB;
import com.example.yulicar.db.MyDao;
import com.example.yulicar.entities.Trip;
import com.example.yulicar.entities.User;
import com.google.android.material.snackbar.Snackbar;
import com.santalu.maskara.widget.MaskEditText;

import java.util.Calendar;

public class BookTripFragment extends Fragment {
    private Long tripId;
    private Trip trip;
    private DBManeger dbManeger;

    private TextView time, direction, date, carName, carNumber;


    private Button btBook;
    private EditText etName;
    private MaskEditText phNumber;
    private Spinner spinNumbOfSeats;
    private int savedNumber;


    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_book_trip, container, false);
        dbManeger = new DBManeger (getContext ());

        time = (TextView) v.findViewById (R.id.book_time);
        direction = (TextView) v.findViewById (R.id.book_direction);
        date = (TextView) v.findViewById (R.id.book_date);
        carName = (TextView) v.findViewById (R.id.book_car);
        carNumber = (TextView) v.findViewById (R.id.book_carNumber);

        btBook = (Button) v.findViewById (R.id.book);
        etName = (EditText) v.findViewById (R.id.et_book_name);
        phNumber = (MaskEditText) v.findViewById (R.id.book_etPhone);
        spinNumbOfSeats = (Spinner) v.findViewById (R.id.spinner_numbOfSeats);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return v;
        } else {
            tripId = bundle.getLong ("tripId");
            trip = dbManeger.dao.getTripById (tripId);
        }
        time.setText (trip.getDate ().get (Calendar.HOUR) + ":" + trip.getDate ().get (Calendar.HOUR));
        direction.setText (trip.getCityFrom ()+ " - "+ trip.getCityTo ());
        date.setText (trip.getDate ().get (Calendar.DAY_OF_MONTH) + "." + trip.getDate ().get (Calendar.MONTH)
        + "." + trip.getDate ().get (Calendar.YEAR));
        carName.setText (trip.getCarName ());
        carNumber.setText (trip.getCarNumber ());


        savedNumber = MainActivity.mSettings.getInt (MainActivity.APP_PREFERENCES_USERID, 0);
        phNumber.setText (String.valueOf (savedNumber));
        User savedUser = dbManeger.dao.getUser (savedNumber);
        etName.setText (savedUser.getName ());


        String[] seats = new String[trip.getNumbOfSeats ()];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = String.valueOf (i+1);
        }
        ArrayAdapter<String> adapterSeats = new ArrayAdapter<String> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, seats);
        spinNumbOfSeats.setAdapter(adapterSeats);




        btBook.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                int numbOfSeats = Integer.parseInt (spinNumbOfSeats.getSelectedItem ().toString ());
                Log.d ("Repair", String.valueOf (tripId));
                User.TripUserJoin order = new User.TripUserJoin (savedNumber, tripId);
                Log.d ("repair", String.valueOf (order.tripId) + " ----- " + String.valueOf (order.phNumber));
                if (order.equals (null)) {
                    Snackbar.make (v,
                            "Запись в базу данных невозможна",
                            Snackbar.LENGTH_LONG).show ();
                    return;
                } else {
                    DBManeger.dao.addTripUserJoin (order);
                    Log.d ("Repair", "запись новой поездки прошла успешно");
                    DBManeger.dao.updateNumbOfSeats (numbOfSeats, tripId);
                    FragmentManager fragmentManager = getActivity ().getSupportFragmentManager ();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }
            }
        });
        return v;
    }
}
