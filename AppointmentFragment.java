package com.example.manipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class AppointmentFragment extends Fragment {



    public AppointmentFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        // Find the button in the fragment's layout
        Button button = view.findViewById(R.id.button1);

        // Set click listener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to move to the new activity
                Intent intent = new Intent(getActivity(), Appointment.class);

                // You can also pass data to the next activity using putExtra
                intent.putExtra("key", "value");

                // Start the new activity
                startActivity(intent);
            }
        });

        return view;
    }
}