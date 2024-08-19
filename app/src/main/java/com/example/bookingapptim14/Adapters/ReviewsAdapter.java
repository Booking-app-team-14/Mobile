package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Review;
import com.example.bookingapptim14.reviews.ReviewsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.RatingBar;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<Review> reviewsList;
    private ReviewsActivity activity;

    public ReviewsAdapter(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }
    public ReviewsAdapter(List<Review> reviewsList, ReviewsActivity activity) {
        this.reviewsList = reviewsList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewsList.get(position);
        holder.reviewSender.setText(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        holder.reviewComment.setText(review.getComment()+ review.getId());
        holder.reviewDateTime.setText(formatDateTime(review.getSentAt()));

        // Postavljanje ocene u RatingBar
        if (review.getRating() == -1) {
            holder.reviewRating.setVisibility(View.GONE); // Sakrij RatingBar ako je ocena -1
        } else {
            holder.reviewRating.setVisibility(View.VISIBLE);
            holder.reviewRating.setRating(review.getRating());
        }
        // Postavljanje onClickListener-a za delete dugme
        holder.deleteButton.setOnClickListener(v -> {
            activity.deleteReviewById(review.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewSender, reviewComment, reviewDateTime;
        RatingBar reviewRating;  // Promena sa TextView na RatingBar
        Button deleteButton;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewSender = itemView.findViewById(R.id.reviewSender);
            reviewComment = itemView.findViewById(R.id.reviewComment);
            reviewRating = itemView.findViewById(R.id.reviewRating);  // Inicijalizacija RatingBar
            reviewDateTime = itemView.findViewById(R.id.reviewDateTime);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    private String formatDateTime(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return ""; // Vraća prazan string ako je dateTime null ili prazan
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());

            Date date = inputFormat.parse(dateTime);
            if (date == null) {
                return ""; // Vraća prazan string ako parsing nije uspeo
            }

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime; // Vraća originalni string ako se desila greška prilikom parsing-a
        }
    }
}


