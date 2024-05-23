package com.example.bookingapptim14.admin.reports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.AdminUserReportsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;

import java.util.ArrayList;
import java.util.List;

public class UserReportsAdminFragment extends Fragment implements AdminUserReportsAdapter.OnReportListener {

    private RecyclerView userReportsRecyclerView;
    private AdminUserReportsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reports_admin, container, false);
        userReportsRecyclerView = view.findViewById(R.id.adminUserReportsRecyclerView);
        adapter = new AdminUserReportsAdapter(new ArrayList<>(), this);
        userReportsRecyclerView.setAdapter(adapter);
        userReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchUserReports();

        return view;
    }

    // GET api/userReports -> UserReportsDTO

    // Convert to UserReportsData

    // GET users/{reportingUserId}/image-type-username -> reportingUserUsername + " | " + reportingUserProfilePictureType + " | " + reportingUserProfilePictureBytes

    // GET users/{reportedUserId}/image-type-username -> reportedUserUsername + " | " + reportedUserProfilePictureType + " | " + reportedUserProfilePictureBytes

    private void fetchUserReports() {
        // TODO: fetch data
        // Convert to UserReportsData

        GlobalData data = GlobalData.getInstance();
        List<UserReportsData> testReports = data.getUserReports();

        adapter.setUserReports(testReports);
    }

    @Override
    public void onUserBlocked(UserReportsData userReport) {
        // TODO: Handle user block logic here

        adapter.removeItem(userReport);
    }

    @Override
    public void onReportDismissed(UserReportsData userReport) {
        // TODO: Handle dismiss logic here

        adapter.removeItem(userReport);
    }

}