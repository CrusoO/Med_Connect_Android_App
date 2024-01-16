package com.example.manipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText un,pass;
    Button  btn;
    TextView tv;
    DBhelper Db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Db = new DBhelper(this);
        un = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        btn = findViewById(R.id.loginbtn );
        tv = findViewById(R.id.regis);
        btn.setOnClickListener(view -> {
            String Name = un.getText().toString();
            String Password = pass.getText().toString();

            if(un.length()==0 || pass.length()==0)
            {
                Toast.makeText(getApplicationContext(),"Please fill all details",Toast.LENGTH_SHORT).show();
            }

            else
            {
                Boolean checkuserpass = Db.checkuserPassword(Name,Password);
                if(checkuserpass){
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),homepage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                }


            }

        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,register.class);
                startActivity(intent);

            }
        });


    }}