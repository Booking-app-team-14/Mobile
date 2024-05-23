package com.example.bookingapptim14.admin.reports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AdminAccommodationReviewReportsAdapter;
import com.example.bookingapptim14.Adapters.AdminUserReportsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;

import java.util.ArrayList;
import java.util.List;

public class AccommodationReviewReportsAdminFragment extends Fragment implements AdminAccommodationReviewReportsAdapter.OnReportListener {

    private RecyclerView userReportsRecyclerView;
    private AdminAccommodationReviewReportsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_review_reports_admin, container, false);
        userReportsRecyclerView = view.findViewById(R.id.adminAccommodationReviewReportsRecyclerView);
        adapter = new AdminAccommodationReviewReportsAdapter(new ArrayList<>(), this);
        userReportsRecyclerView.setAdapter(adapter);
        userReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAccommodationReviewReports();

        return view;
    }

    // GET api/reviewReports -> AccommodationReviewReportsDTO

    // Convert to AccommodationReviewReportsData

    // GET /accommodationReviews/{reviewId} -> AccommodationReviewDTO

    // GET users/{id}/image-type-username -> userUsername + " | " + userProfilePictureType + " | " + userProfilePictureBytes

    // GET accommodations/{accommodationId}/nameAndTypeAndBytes -> accommodationName + " | " + accommodationType + " | " + accommodationImageBytes

    public void fetchAccommodationReviewReports() {
        // TODO: fetch data
        // Convert to AccommodationReviewReportsData

        GlobalData data = GlobalData.getInstance();
        List<AccommodationReviewReportsData> testReports = data.getAccommodationReviewReports();

        adapter.setAccommodationReviewReports(testReports);
    }

    // Remove accommodation review: DELETE api/reviewReports/accommodationReviews/{reviewReportId}
    @Override
    public void onReviewRemoved(AccommodationReviewReportsData report) {
        // TODO: Handle user block logic here

        adapter.removeItem(report);
    }

    // Dismiss accommodation review report: DELETE api/reviewReports/{reviewReportId}
    @Override
    public void onReviewReportDismissed(AccommodationReviewReportsData report) {
        // TODO: Handle dismiss logic here

        adapter.removeItem(report);
    }

    @Override
    public void onDetailsRequested(AccommodationReviewReportsData report) {
        // TODO: Open details fragment here
    }

}