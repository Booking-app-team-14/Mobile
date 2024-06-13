package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.approval.CommentsAndReviewsApprovalOwnerViewHolder;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsData;

import java.util.List;

public class AdminApprovalOwnerCommentsAndReviewsAdapter extends RecyclerView.Adapter<CommentsAndReviewsApprovalOwnerViewHolder> {

    private List<ApproveOwnerReviewsData> commentsAndReviews;
    private AdminApprovalOwnerCommentsAndReviewsAdapter.OnOwnerActionListener listener;

    public AdminApprovalOwnerCommentsAndReviewsAdapter(List<ApproveOwnerReviewsData> commentsAndReviews, AdminApprovalOwnerCommentsAndReviewsAdapter.OnOwnerActionListener listener) {
        this.commentsAndReviews = commentsAndReviews;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentsAndReviewsApprovalOwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_owner_comments_and_reviews, parent, false);
        return new CommentsAndReviewsApprovalOwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAndReviewsApprovalOwnerViewHolder holder, int position) {
        ApproveOwnerReviewsData commentAndReview = commentsAndReviews.get(position);
        holder.bind(commentAndReview);

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOwnerCommentAndReviewApproved(commentAndReview);
            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOwnerCommentAndReviewRejected(commentAndReview);
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentsAndReviews.size();
    }

    public void setOwnerCommentsAndReviews(List<ApproveOwnerReviewsData> commentsAndReviews) {
        this.commentsAndReviews = commentsAndReviews;
        notifyDataSetChanged();
    }

    public void removeItem(ApproveOwnerReviewsData commentAndReview) {
        int position = commentsAndReviews.indexOf(commentAndReview);
        if (position == -1) {
            return;
        }
        commentsAndReviews.remove(position);
    }

    public interface OnOwnerActionListener {
        void onOwnerCommentAndReviewApproved(ApproveOwnerReviewsData commentAndReview);
        void onOwnerCommentAndReviewRejected(ApproveOwnerReviewsData commentAndReview);
    }

}
