package com.example.yulicar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yulicar.activities.MainActivity;
import com.example.yulicar.R;
import com.example.yulicar.db.DBManeger;
import com.example.yulicar.db.entities.User;
import com.google.android.material.snackbar.Snackbar;


public class UserFragment extends Fragment {

    DBManeger dbManeger;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance (String param1, String param2) {
        UserFragment fragment = new UserFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate (R.layout.fragment_account, container, false);
        TextView name = (TextView) v.findViewById (R.id.name);
        dbManeger = new DBManeger (getContext ());

        TextView number = (TextView) v.findViewById (R.id.number);
        int savedPhNumber = MainActivity.mSettings.getInt(MainActivity.APP_PREFERENCES_USERID, 0);
        User savedUser = dbManeger.dao.getUser (savedPhNumber);
        if (savedUser != null) {
        name.setText (savedUser.getName ());}
        String strSavedNumber = String.valueOf (savedPhNumber);
        strSavedNumber = "+375 (" + strSavedNumber.charAt (0) + strSavedNumber.charAt (1) +
                ") "+ strSavedNumber.substring (2,5) + " " + strSavedNumber.substring (5,7) + " " +
                strSavedNumber.substring (7,9);
        number.setText (String.valueOf (strSavedNumber));
        Button myMiles = (Button) v.findViewById (R.id.miles);
        myMiles.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                Snackbar.make (v,
                        "Скоро всё появится))",
                        Snackbar.LENGTH_LONG).show ();
            }
        });

        Button myTrips = (Button) v.findViewById (R.id.my_trips);
        myTrips.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                UserTripsFragment userTripsFragment = new UserTripsFragment ();
                FragmentManager fragmentManager = getActivity ().getSupportFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();
                fragmentTransaction.replace (R.id.fragment_conteiner, userTripsFragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();
            }
        });

        Button signOff = (Button) v.findViewById (R.id.sing_off);
        signOff.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                MainActivity.setHasVisited (false);
                startActivity (new Intent (getActivity (), MainActivity.class));
                getActivity ().finish ();
            }
        });
        return v;
    }
}