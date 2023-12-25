package com.example.bookingapptim14.Adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback;

import com.example.bookingapptim14.admin.approval.AccommodationApprovalFragment;
import com.example.bookingapptim14.admin.approval.CommentsAndReviewsApprovalFragment;

import java.util.Arrays;
import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragments = Arrays.asList(
            new AccommodationApprovalFragment(),
            new CommentsAndReviewsApprovalFragment()
    );

    public MyViewPagerAdapter(Fragment fragment) {
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


