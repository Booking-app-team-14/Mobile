package com.example.bookingapptim14.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.bookingapptim14.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivityGuest extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.navHomeGuest) {
                loadFragment(new HomeFragmentGuest(), false);
            }
            if (itemId == R.id.navLikedGuest) {
                loadFragment(new SavedFragmentGuest(), false);
            }
            if (itemId == R.id.navNotificationsGuest) {
                loadFragment(new NotificationsFragmentGuest(), false);
            }
            if (itemId == R.id.navProfileGuest) {
                loadFragment(new ProfileFragmentGuest(), false);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guest);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new HomeFragmentGuest(), true);

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
        if (currentFragment instanceof HomeFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navHomeGuest);
        } else if (currentFragment instanceof SavedFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navLikedGuest);
        } else if (currentFragment instanceof NotificationsFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navNotificationsGuest);
        } else if (currentFragment instanceof ProfileFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navProfileGuest);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}