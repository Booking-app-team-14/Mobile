package com.example.bookingapptim14.admin.reports;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AdminAccommodationReviewReportsAdapter;
import com.example.bookingapptim14.Adapters.AdminOwnerReviewReportAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;

import java.util.ArrayList;
import java.util.List;

public class OwnerReviewReportsAdminFragment extends Fragment implements AdminOwnerReviewReportAdapter.OnReportListener {

    private RecyclerView userReportsRecyclerView;
    private AdminOwnerReviewReportAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_review_reports_admin, container, false);
        userReportsRecyclerView = view.findViewById(R.id.adminOwnerReviewReportsRecyclerView);
        adapter = new AdminOwnerReviewReportAdapter(new ArrayList<>(), this);
        userReportsRecyclerView.setAdapter(adapter);
        userReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAccommodationReviewReports();

        return view;
    }

    // GET api/ownerReviewReports -> OwnerReviewReportsDTO

    // GET /api/reviews/{reviewId} -> OwnerReviewDTO

    // GET users/{senderId}/image-type-username -> senderUsername + " | " + senderProfilePictureType + " | " + senderProfilePictureBytes

    // GET users/{recipientId}/image-type-username -> recipientUsername + " | " + recipientProfilePictureType + " | " + recipientProfilePictureBytes

    // GET api/owners/{recipientId}/ratingString -> ratingBeforeString

    public void fetchAccommodationReviewReports() {
        // TODO: fetch data
        // Convert to OwnerReviewReportsData

        GlobalData data = GlobalData.getInstance();
        List<OwnerReviewReportsData> testReports = data.getOwnerReviewReports();

        adapter.setOwnerReviewReports(testReports);
    }

    // DELETE api/ownerReviewReports/ownerReviews/{reviewReportId}
    @Override
    public void onReviewRemoved(OwnerReviewReportsData report) {
        // TODO: Handle user block logic here

        adapter.removeItem(report);
    }

    // DELETE api/ownerReviewReports/{reviewReportId}
    @Override
    public void onReviewReportDismissed(OwnerReviewReportsData report) {
        // TODO: Handle dismiss logic here

        adapter.removeItem(report);
    }

}