package com.example.yulicar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

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
        View view = inflater.inflate (R.layout.fragment_search, null);
        /*Button button = (Button) view.findViewById (R.id.ButtonCalendar);
        button.setOnClickListener (this::onClick);*/

        // Inflate the layout for this fragment
        return view;
    }

    public void onClick (View v) {

        Snackbar.make (v, "HELLO FROM BUTTON", Snackbar.LENGTH_LONG).show ();
        /*Dialog dialog = new Dialog (getActivity ().getBaseContext ());
        dialog.setContentView (R.layout.alert_calendar);

        dialog.setCancelable (false);*/
        //CalendarView calendarView = (CalendarView) fi


    }
}