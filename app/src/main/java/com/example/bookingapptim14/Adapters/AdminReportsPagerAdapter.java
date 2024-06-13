package com.example.bookingapptim14.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bookingapptim14.admin.approval.AccommodationApprovalFragment;
import com.example.bookingapptim14.admin.approval.CommentsAndReviewsApprovalFragment;
import com.example.bookingapptim14.admin.reports.AccommodationReviewReportsAdminFragment;
import com.example.bookingapptim14.admin.reports.OwnerReviewReportsAdminFragment;
import com.example.bookingapptim14.admin.reports.UserReportsAdminFragment;

import java.util.Arrays;
import java.util.List;

public class AdminReportsPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragments = Arrays.asList(
            new UserReportsAdminFragment(),
            new AccommodationReviewReportsAdminFragment(),
            new OwnerReviewReportsAdminFragment()
    );

    public AdminReportsPagerAdapter(Fragment fragment) {
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