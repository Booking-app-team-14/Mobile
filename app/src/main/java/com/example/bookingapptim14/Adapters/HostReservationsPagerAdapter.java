package com.example.bookingapptim14.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookingapptim14.host.ApprovedReservationsFragmentHost;
import com.example.bookingapptim14.host.ReservationRequestsFragmentHost;

import java.util.Arrays;
import java.util.List;

public class HostReservationsPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragments = Arrays.asList(
            new ApprovedReservationsFragmentHost(),
            new ReservationRequestsFragmentHost()
    );

    public HostReservationsPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

}
