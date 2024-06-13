package com.example.bookingapptim14.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.bookingapptim14.host.MainActivityHost;
import com.example.bookingapptim14.models.User;
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
            if (itemId == R.id.navApprovalAdmin) {
                loadFragment(new ApprovalFragmentAdmin(), false);
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if ((currentFragment instanceof UpdateAccountFragment) || (currentFragment instanceof UpdateAccountPasswordFragment)) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        super.onBackPressed();
        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (previousFragment instanceof HomeFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navHomeAdmin);
        } else if (previousFragment instanceof ReportsFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navReportsAdmin);
        } else if (previousFragment instanceof ApprovalFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navApprovalAdmin);
        } else if (previousFragment instanceof ProfileFragmentAdmin) {
            bottomNavigationView.setSelectedItemId(R.id.navProfileAdmin);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void signOut(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("jwtToken");
        editor.remove("userId");
        editor.apply();
        Intent intent = new Intent(MainActivityAdmin.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

}