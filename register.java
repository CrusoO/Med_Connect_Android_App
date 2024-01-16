package com.example.manipal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class register extends AppCompatActivity {
    EditText fn, em, dt, ph, pass;
    Button btn;
    private RadioGroup rd;
    private RadioButton rb;
    private ProgressBar progressBar;
    int year, month, day;

    DBhelper DB;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toast.makeText(this, "You can register now", Toast.LENGTH_LONG).show();

        fn = findViewById(R.id.name);
        em = findViewById(R.id.Email);
        ph = findViewById(R.id.editTextPhone);
        pass = findViewById(R.id.Password);
        dt = findViewById(R.id.Date);
        progressBar = findViewById(R.id.progressBar);
        DB = new DBhelper(this);
        rd = findViewById(R.id.radio_group_register_gender);
        rd.clearCheck();

        Calendar calendar = Calendar.getInstance();

        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDayOfMonth);
                        dt.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        Button btn = findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = fn.getText().toString().trim();
                String Date = dt.getText().toString().trim();
                String Password = pass.getText().toString().trim();
                String Email = em.getText().toString().trim();
                String pnum = ph.getText().toString().trim();
                String textGender;
                int genderId = rd.getCheckedRadioButtonId();
                rb = findViewById(genderId);

                if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Date) || TextUtils.isEmpty(pnum)) {
                    Toast.makeText(register.this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
                } else if (Password.length() < 6) {
                    Toast.makeText(register.this, "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(register.this, "Enter a valid email", Toast.LENGTH_LONG).show();
                } else if (genderId == -1) {
                    Toast.makeText(register.this, "Please select your gender", Toast.LENGTH_LONG).show();
                } else if (pnum.length() != 10) {
                    Toast.makeText(register.this, "Password should be at least 6 characters", Toast.LENGTH_LONG).show();
                } else {
                    textGender = rb.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);

                    Boolean checkuser = DB.checkuser(Name);
                    if (!checkuser) {
                        Boolean insert = DB.insertData(Name, Password);
                        if (insert) {
                            Toast.makeText(register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(register.this, "User Already Exists. Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}


