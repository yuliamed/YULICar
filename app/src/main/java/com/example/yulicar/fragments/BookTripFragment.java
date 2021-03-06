package com.example.yulicar.fragments;

import android.os.Bundle;
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

import com.example.yulicar.R;
import com.example.yulicar.activities.MainActivity;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.Location;
import com.example.yulicar.db.entities.Trip;
import com.example.yulicar.db.entities.User;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

//import com.santalu.maskara.widget.MaskEditText;

public class BookTripFragment extends Fragment {
    private Long tripId;
    private Trip trip;
    private DBManeger dbManeger;

    private TextView time, direction, date, carName, carNumber;


    private Button btBook;
    private EditText etName;
    private MaskedEditText phNumber;
    private Spinner spinNumbOfSeats;
    private int savedNumber;
    private Spinner spinLocation;


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
        phNumber = (MaskedEditText) v.findViewById (R.id.book_etPhone);
        spinNumbOfSeats = (Spinner) v.findViewById (R.id.spinner_numbOfSeats);
        spinLocation = (Spinner)v.findViewById (R.id.spinner_location);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return v;
        } else {
            tripId = bundle.getLong ("tripId");
            trip = dbManeger.dao.getTripById (tripId);
        }
        String hour = OneTripFragment.changeFormatOfNumbString (trip.getDate ().get (Calendar.HOUR));
        String minute = OneTripFragment.changeFormatOfNumbString (trip.getDate ().get (Calendar.MINUTE));
        time.setText ( hour + ":" + minute);
        direction.setText (trip.getCityFrom ()+ " - "+ trip.getCityTo ());
        String day = OneTripFragment.changeFormatOfNumbString (trip.getDate ().get (Calendar.DAY_OF_MONTH));
        String month = OneTripFragment.changeFormatOfNumbString (trip.getDate ().get (Calendar.MONTH));
        date.setText (day + "." + month + "." + trip.getDate ().get (Calendar.YEAR));
        carName.setText (trip.getCarName ());
        carNumber.setText (trip.getCarNumber ());


        savedNumber = MainActivity.mSettings.getInt (MainActivity.APP_PREFERENCES_USERID, 0);
        phNumber.setMaskedText (String.valueOf (savedNumber));
        User savedUser = dbManeger.dao.getUser (savedNumber);
        etName.setText (savedUser.getName ());


        String[] seats = new String[trip.getNumbOfSeats ()];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = String.valueOf (i+1);
        }
        ArrayAdapter<String> adapterSeats = new ArrayAdapter<String> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, seats);
        spinNumbOfSeats.setAdapter(adapterSeats);

        List<Location> locations = dbManeger.dao.getLocationsForCity (trip.getCityFrom ());
        String[] locationsStr = new String[locations.size ()];
        for (int i =0; i< locationsStr.length;i++) {
            locationsStr[i] = locations.get (i).getLocation ();
        }
        ArrayAdapter<String> spinLocations = new ArrayAdapter<> (getActivity (), R.layout.row_for_spinner, R.id.row_of_spinner, locationsStr);
        spinLocation.setAdapter (spinLocations);


        btBook.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                int numbOfSeats = Integer.parseInt (spinNumbOfSeats.getSelectedItem ().toString ());
                String location = spinLocation.getSelectedItem ().toString ();
                Integer locationId = -1;
                for (Location l : locations) {
                    if (l.getLocation ().equals (location)) {
                        locationId=l.getLocationId ();
                        break;
                    }
                }
                if (locationId.equals (-1)) {
                    Snackbar.make (v,"???????????? ???????????????????? ?????????????? ?? ???????? ????????????", Snackbar.LENGTH_LONG).show ();
                    return;
                }
                Log.d ("Repair", String.valueOf (tripId));
                User.TripUserJoin order = new User.TripUserJoin (savedNumber, tripId, locationId, numbOfSeats);
                Log.d ("repair", String.valueOf (order.tripId) + " ----- " + String.valueOf (order.phNumber) + " -------- " + String.valueOf (order.getNumbOfSeats ()));
                if (order.equals (null)) {
                    Snackbar.make (v,
                            "???????????? ?? ???????? ???????????? ????????????????????",
                            Snackbar.LENGTH_LONG).show ();
                    return;
                } else {
                    DBManeger.dao.addTripUserJoin (order);
                    Log.d ("Repair", "???????????? ?????????? ?????????????? ???????????? ??????????????");
                    DBManeger.dao.updateNumbOfSeats (numbOfSeats, tripId);
                    FragmentManager fragmentManager = getActivity ().getSupportFragmentManager ();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }
            }
        });
        return v;
    }
}
