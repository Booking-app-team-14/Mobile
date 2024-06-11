package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.approval.CommentsAndReviewsApprovalAccommodationViewHolder;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;

import java.util.List;

public class AdminApprovalAccommodationCommentsAndReviewsAdapter extends RecyclerView.Adapter<CommentsAndReviewsApprovalAccommodationViewHolder> {

    private List<ApproveAccommodationReviewsData> commentsAndReviews;
    private AdminApprovalAccommodationCommentsAndReviewsAdapter.OnAccommodationActionListener listener;

    public AdminApprovalAccommodationCommentsAndReviewsAdapter(List<ApproveAccommodationReviewsData> commentsAndReviews, AdminApprovalAccommodationCommentsAndReviewsAdapter.OnAccommodationActionListener listener) {
        this.commentsAndReviews = commentsAndReviews;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentsAndReviewsApprovalAccommodationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_accommodation_comments_and_reviews, parent, false);
        return new CommentsAndReviewsApprovalAccommodationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAndReviewsApprovalAccommodationViewHolder holder, int position) {
        ApproveAccommodationReviewsData commentAndReview = commentsAndReviews.get(position);
        holder.bind(commentAndReview);

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationCommentAndReviewApproved(commentAndReview);
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationCommentAndReviewRejected(commentAndReview);
            }
        });

        holder.viewAccommodationDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationCommentAndReviewDetailsRequested(commentAndReview);
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentsAndReviews.size();
    }

    public void setAccommodationCommentsAndReviews(List<ApproveAccommodationReviewsData> commentsAndReviews) {
        this.commentsAndReviews = commentsAndReviews;
        notifyDataSetChanged();
    }

    public void removeItem(ApproveAccommodationReviewsData commentAndReview) {
        int position = commentsAndReviews.indexOf(commentAndReview);
        if (position == -1) {
            return;
        }
        commentsAndReviews.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnAccommodationActionListener {
        void onAccommodationCommentAndReviewApproved(ApproveAccommodationReviewsData commentAndReview);
        void onAccommodationCommentAndReviewRejected(ApproveAccommodationReviewsData commentAndReview);
        void onAccommodationCommentAndReviewDetailsRequested(ApproveAccommodationReviewsData commentAndReview);
    }

}
