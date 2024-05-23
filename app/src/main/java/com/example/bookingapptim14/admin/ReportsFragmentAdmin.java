package com.example.bookingapptim14.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AdminReportsPagerAdapter;
import com.example.bookingapptim14.Adapters.MyViewPagerAdapter;
import com.example.bookingapptim14.R;
import com.google.android.material.tabs.TabLayout;

public class ReportsFragmentAdmin extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports_admin, container, false);

        tabLayout = view.findViewById(R.id.adminReportsTabLayout);
        viewPager = view.findViewById(R.id.adminReportsViewPager);

        AdminReportsPagerAdapter adapter = new AdminReportsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        return view;
    }

}