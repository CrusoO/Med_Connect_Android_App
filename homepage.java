package com.example.manipal;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

/** @noinspection deprecation*/
public class homepage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationview;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = findViewById(R.id.drawer);
        navigationview = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_close, R.string.navigation_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new AFragment());

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.appointment) {
                    loadFragment(new AppointmentFragment());
                    Toast.makeText(homepage.this, "", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.home_menu) {
                    loadFragment(new AFragment());
                } else if (id == R.id.testbooking) {
                    loadFragment(new articles());
                } else if (id == R.id.MyDoctor) {
                    loadFragment(new DoctorFragment());
                } else if (id == R.id.help) {
                    loadFragment(new helpFragment());
                } else if (id == R.id.settings) {
                    loadFragment(new SettingsFragment());
                } else {
                    Toast.makeText(homepage.this, "Other Clicked", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // If the drawer is not open, perform the default back button behavior
                    if (isEnabled()) {
                        onBackPressed();
                    }
                }
            }
        };

        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}
