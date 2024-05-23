package com.example.bookingapptim14.admin.approval;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.AdminApprovalAccommodationCommentsAndReviewsAdapter;
import com.example.bookingapptim14.Adapters.AdminApprovalOwnerCommentsAndReviewsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsData;

import java.util.ArrayList;
import java.util.List;

public class CommentsAndReviewsApprovalFragment extends Fragment implements AdminApprovalAccommodationCommentsAndReviewsAdapter.OnAccommodationActionListener, AdminApprovalOwnerCommentsAndReviewsAdapter.OnOwnerActionListener {

    private RecyclerView accommodationCommentsAndReviewsApprovalRecyclerView;
    private RecyclerView ownerCommentsAndReviewsApprovalRecyclerView;
    private AdminApprovalAccommodationCommentsAndReviewsAdapter accommodationCommentsAndReviewsAdapter;
    private AdminApprovalOwnerCommentsAndReviewsAdapter ownerCommentsAndReviewsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments_and_reviews_approval, container, false);

        // Get references to the RecyclerViews
        accommodationCommentsAndReviewsApprovalRecyclerView = view.findViewById(R.id.adminCommentsAndReviewsAccommodationsRecyclerView);
        ownerCommentsAndReviewsApprovalRecyclerView = view.findViewById(R.id.adminCommentsAndReviewsOwnersRecyclerView);

        // Initialize the adapters
        accommodationCommentsAndReviewsAdapter = new AdminApprovalAccommodationCommentsAndReviewsAdapter(new ArrayList<>(), this);
        ownerCommentsAndReviewsAdapter = new AdminApprovalOwnerCommentsAndReviewsAdapter(new ArrayList<>(), this);

        // Set the adapters to the RecyclerViews
        accommodationCommentsAndReviewsApprovalRecyclerView.setAdapter(accommodationCommentsAndReviewsAdapter);
        ownerCommentsAndReviewsApprovalRecyclerView.setAdapter(ownerCommentsAndReviewsAdapter);

        // Set the layout managers to the RecyclerViews
        accommodationCommentsAndReviewsApprovalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ownerCommentsAndReviewsApprovalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initially, let's assume that the accommodationCommentsAndReviewsApprovalRecyclerView is visible
        accommodationCommentsAndReviewsApprovalRecyclerView.setVisibility(View.VISIBLE);
        ownerCommentsAndReviewsApprovalRecyclerView.setVisibility(View.GONE);

        // Get reference to the toggle button
        ToggleButton toggleButton = view.findViewById(R.id.adminCommentsAndReviewsToggleButton);

        // Set an OnCheckedChangeListener
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled/checked
                    accommodationCommentsAndReviewsApprovalRecyclerView.setVisibility(View.GONE);
                    ownerCommentsAndReviewsApprovalRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled/unchecked
                    accommodationCommentsAndReviewsApprovalRecyclerView.setVisibility(View.VISIBLE);
                    ownerCommentsAndReviewsApprovalRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        fetchCommentsAndReviews();

        return view;
    }

// Accommodation comments and reviews are fetched from the server

    // GET api/reviews/accommodation/requests -> ApproveReviewsDTO

    // Convert to ApproveAccommodationReviewsData

    // GET /users/{id}/image-type-username" -> guestUsername + " | " + guestProfilePictureType + " | " + guestProfilePictureBytes

    // GET accommodations/{id}/nameAndType -> accommodationName + " | " + accommodationType

    // GET accommodations/accommodations/{Id}/image -> accommodationPictureBytes as String

    // GET /accommodations/{id}/ratingString -> rating as String

// Owner comments and reviews are fetched from the server

    // GET /owner/requests -> ApproveOwnerReviewsDTO

    // GET /users/{guestId}/image-type-username" -> guestUsername + " | " + guestProfilePictureType + " | " + guestProfilePictureBytes

    // GET /users/{ownerId}/image-type-username" -> ownerUsername + " | " + ownerProfilePictureType + " | " + ownerProfilePictureBytes

    // GET /owners/{id}/ratingString -> ratingBefore as String (if -1 do not display)

    private void fetchCommentsAndReviews() {
        // TODO: Replace with actual data fetching logic
        // Convert to ApproveAccommodationReviewsData

        // For now, we'll use dummy data

        GlobalData data = GlobalData.getInstance();
        List<ApproveAccommodationReviewsData> testAccommodationCommentsAndReviews = data.getAccommodationCommentsAndReviews();
        List<ApproveOwnerReviewsData> testOwnerCommentsAndReviews = data.getOwnerCommentsAndReviews();

        accommodationCommentsAndReviewsAdapter.setAccommodationCommentsAndReviews(testAccommodationCommentsAndReviews);
        ownerCommentsAndReviewsAdapter.setOwnerCommentsAndReviews(testOwnerCommentsAndReviews);
    }

    @Override
    public void onAccommodationCommentAndReviewApproved(ApproveAccommodationReviewsData commentAndReview) {
        // TODO: Handle approval logic here
        // PUT /reviews/admin/accommodation/{accommodationId}
        accommodationCommentsAndReviewsAdapter.removeItem(commentAndReview);
    }

    @Override
    public void onAccommodationCommentAndReviewRejected(ApproveAccommodationReviewsData commentAndReview) {
        // TODO: Handle rejection logic here
        // DELETE /reviews/admin/accommodation/{accommodationId}
        accommodationCommentsAndReviewsAdapter.removeItem(commentAndReview);
    }

    @Override
    public void onAccommodationCommentAndReviewDetailsRequested(ApproveAccommodationReviewsData commentAndReview) {
        // TODO: Open accommodation details fragment here
    }

    @Override
    public void onOwnerCommentAndReviewApproved(ApproveOwnerReviewsData commentAndReview) {
//         TODO: Handle approval logic here
//         PUT reviews/admin/{reviewId}
        ownerCommentsAndReviewsAdapter.removeItem(commentAndReview);
    }

    @Override
    public void onOwnerCommentAndReviewRejected(ApproveOwnerReviewsData commentAndReview) {
//         TODO: Handle rejection logic here
//         DELETE reviews/admin/{reviewId}
        ownerCommentsAndReviewsAdapter.removeItem(commentAndReview);
    }

}
