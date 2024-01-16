package com.example.manipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Find the TextView elements by their IDs
        TextView textView1 = view.findViewById(R.id.textView20);
        TextView textView2 = view.findViewById(R.id.textView21);
        TextView textView3 = view.findViewById(R.id.textView22);

        // Set click listeners for each TextView
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for TextView 1
                showToast("Logged Out Successfully");

                // Start the first activity
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for TextView 2
                showToast("TextView 2 clicked");

                // Start the second activity
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for TextView 3
                showToast("TextView 3 clicked");

                // Start the third activity
                Intent intent = new Intent(getContext(), helpFragment.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
