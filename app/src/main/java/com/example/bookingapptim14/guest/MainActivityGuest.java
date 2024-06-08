package com.example.bookingapptim14.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.LoginScreen;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.UpdateAccountFragment;
import com.example.bookingapptim14.UpdateAccountPasswordFragment;
import com.example.bookingapptim14.models.User;
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if ((currentFragment instanceof UpdateAccountFragment) || (currentFragment instanceof UpdateAccountPasswordFragment)) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        super.onBackPressed();
        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (previousFragment instanceof HomeFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navHomeGuest);
        } else if (previousFragment instanceof SavedFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navLikedGuest);
        } else if (previousFragment instanceof NotificationsFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navNotificationsGuest);
        } else if (previousFragment instanceof ProfileFragmentGuest) {
            bottomNavigationView.setSelectedItemId(R.id.navProfileGuest);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void signOut(View view) {
        // TODO: remove global data
        GlobalData gd = GlobalData.getInstance();
        gd.setLoggedInUser(new User());
        //
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("jwtToken");
        editor.remove("userId");
        editor.apply();
        Intent intent = new Intent(MainActivityGuest.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

}