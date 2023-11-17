package com.example.bookingapptim14.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.guest.HomeFragmentGuest;
import com.example.bookingapptim14.guest.NotificationsFragmentGuest;
import com.example.bookingapptim14.guest.ProfileFragmentGuest;
import com.example.bookingapptim14.guest.SavedFragmentGuest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityAdmin extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.navHomeAdmin) {
                loadFragment(new HomeFragmentAdmin(), false);
            }
            if (itemId == R.id.navReportsAdmin) {
                loadFragment(new ReportsFragmentAdmin(), false);
            }
            if (itemId == R.id.navRequestsAdmin) {
                loadFragment(new RequestsFragmentAdmin(), false);
            }
            if (itemId == R.id.navProfileAdmin) {
                loadFragment(new ProfileFragmentAdmin(), false);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragmentAdmin(), true);

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
        if (currentFragment instanceof HomeFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navHomeAdmin);
        } else if (currentFragment instanceof ReportsFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navReportsAdmin);
        } else if (currentFragment instanceof RequestsFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navRequestsAdmin);
        } else if (currentFragment instanceof ProfileFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navProfileAdmin);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}