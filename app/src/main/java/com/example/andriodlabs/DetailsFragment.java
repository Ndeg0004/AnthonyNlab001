package com.example.andriodlabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class DetailsFragment extends Fragment {

    private TextView FillOneText;
    private TextView FillTwoText;
    private TextView FillThreeText;

    public static DetailsFragment newInstance(String param1, String param2,String param3) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("Name", param1);
        args.putString("Height", param2);
        args.putString("Mass",param3);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    public void onViewCreated( View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
        // getting the textview from fragment details.
         FillOneText = view.findViewById(R.id.fillOne);
         FillTwoText = view.findViewById(R.id.fillTwo);
         FillThreeText = view.findViewById(R.id.fillThree);

        // get the bundle when passed when the fragment is created
        Bundle bundle = getArguments();
        if(bundle != null) {
            String name = bundle.getString("Name");
            String height = bundle.getString("Height");
            String mass = bundle.getString("Mass");

            //sets the values to Textview
            FillOneText.setText(name);
            FillTwoText.setText(height);
            FillThreeText.setText(mass);


        }

    }
}