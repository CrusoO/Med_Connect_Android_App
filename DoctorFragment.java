package com.example.manipal;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DoctorFragment extends Fragment {

    private Spinner spinnerDoctor;
    private DatePicker datePicker;
    private Button buttonBookAppointment;

    public DoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);

        // Initialize UI elements
        spinnerDoctor = view.findViewById(R.id.spinnerDoctor);
        datePicker = view.findViewById(R.id.datePicker);
        buttonBookAppointment = view.findViewById(R.id.buttonBookAppointment);

        // Set up the spinner with dummy doctor names (replace with actual data)
        List<String> doctorNames = new ArrayList<>();
        doctorNames.add("Dr. Smith");
        doctorNames.add("Dr. Johnson");
        doctorNames.add("Dr. Williams");

        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, doctorNames);
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(doctorAdapter);

        // Set the default date to the current date
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        // Set up a listener for the spinner item selection (optional)
        spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle selection if needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Set up a listener for the button click
        buttonBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle booking appointment logic
                String selectedDoctor = spinnerDoctor.getSelectedItem().toString();
                Log.d("DoctorFragment", "Button clicked");

                // Get selected date
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is zero-based
                int year = datePicker.getYear();

                // Perform any additional logic, such as saving the appointment to a database
                String appointmentDate = year + "-" + month + "-" + day;

                try {
                    DBhelper dbHelper = new DBhelper(getActivity());

                    boolean isInserted = dbHelper.insertAppointment(selectedDoctor, "loggedInUser", appointmentDate, "otherData");

                    // Example: Display a toast message
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Example: Display a toast message
                            if (isInserted) {
                                // Example: Display a toast message if the appointment is successfully booked
                                Toast.makeText(getActivity(), "Appointment booked with " + selectedDoctor + " on " + appointmentDate, Toast.LENGTH_SHORT).show();

                                // Retrieve and display appointments for the selected doctor
                                Cursor appointmentsCursor = dbHelper.getAppointmentsForDoctor(selectedDoctor);
                                if (appointmentsCursor.moveToFirst()) {
                                    do {
                                        // Retrieve and handle each appointment record
                                        String appointmentUser = appointmentsCursor.getString(appointmentsCursor.getColumnIndex("user"));
                                        String appointmentDate = appointmentsCursor.getString(appointmentsCursor.getColumnIndex("appointmentDate"));
                                        // Handle the appointment record as needed (e.g., display, store, etc.)
                                    } while (appointmentsCursor.moveToNext());
                                }
                                appointmentsCursor.close();
                            } else {
                                // Example: Display a toast message if there's an issue with booking the appointment
                                Toast.makeText(getActivity(), "Failed to book appointment. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("DoctorFragment", "Error in database operation: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
