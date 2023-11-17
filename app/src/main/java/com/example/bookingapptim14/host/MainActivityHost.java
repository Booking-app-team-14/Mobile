package com.example.bookingapptim14.host;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.bookingapptim14.host.HomeFragmentHost;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.HomeFragmentAdmin;
import com.example.bookingapptim14.admin.ProfileFragmentAdmin;
import com.example.bookingapptim14.admin.ReportsFragmentAdmin;
import com.example.bookingapptim14.admin.RequestsFragmentAdmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityHost extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.navHomeHost) {
                loadFragment(new HomeFragmentHost(), false);
            }
            if (itemId == R.id.navReservationsHost) {
                loadFragment(new ReservationsFragmentHost(), false);
            }
            if (itemId == R.id.navNotificationsHost) {
                loadFragment(new NotificationsFragmentHost(), false);
            }
            if (itemId == R.id.navProfileHost) {
                loadFragment(new ProfileFragmentHost(), false);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_host);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragmentHost(), true);

    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            String fragmentTag = fragment.getClass().getName();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(fragmentTag, 0);
            if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) {
                fragmentTransaction.replace(R.id.frameLayout, fragment, fragmentTag);
                fragmentTransaction.addToBackStack(fragmentTag);
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onBackPressed();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof HomeFragmentHost) {
            bottomNavigationView.setSelectedItemId(R.id.navHomeHost);
        } else if (currentFragment instanceof ReservationsFragmentHost) {
            bottomNavigationView.setSelectedItemId(R.id.navReservationsHost);
        } else if (currentFragment instanceof NotificationsFragmentHost) {
            bottomNavigationView.setSelectedItemId(R.id.navNotificationsHost);
        } else if (currentFragment instanceof ProfileFragmentHost) {
            bottomNavigationView.setSelectedItemId(R.id.navProfileHost);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}