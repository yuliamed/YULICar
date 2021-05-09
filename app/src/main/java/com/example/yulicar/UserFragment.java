package com.example.yulicar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class UserFragment extends Fragment {

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
       // name.setText (MainActivity.APP_PREFERENCES_USERNAME.toString ());
        TextView number = (TextView) v.findViewById (R.id.number);
        //name.setText (MainActivity.APP_PREFERENCES_USERNUMBER);
        Button myMiles = (Button) v.findViewById (R.id.miles);
        Button myTrips = (Button) v.findViewById (R.id.my_trips);
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