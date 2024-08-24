package com.example.bookingapptim14.Adapters;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

    private long userId;

    private String userRole;

    public ReviewsAdapter(List<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }
//    public ReviewsAdapter(List<Review> reviewsList, ReviewsActivity activity, long userId) {
//        this.reviewsList = reviewsList;
//        this.activity = activity;
//        this.userId= userId;
//    }

    public ReviewsAdapter(List<Review> reviewsList, ReviewsActivity activity, long userId, String userRole) {
        this.reviewsList = reviewsList;
        this.activity = activity;
        this.userId = userId;
        this.userRole = userRole;
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
        holder.reviewComment.setText(review.getComment());
        holder.reviewDateTime.setText(formatDateTime(review.getSentAt()));

        // Postavljanje ocene u RatingBar
        if (review.getRating() == -1) {
            holder.reviewRating.setVisibility(View.GONE); // Sakrij RatingBar ako je ocena -1
        } else {
            holder.reviewRating.setVisibility(View.VISIBLE);
            holder.reviewRating.setRating(review.getRating());
        }


        // Prikazivanje dugmeta u zavisnosti od uloge korisnika
        if (userRole.equals("GUEST")) {
            // GUEST - prikazivanje dugmeta za brisanje samo ako je recenziju postavio trenutni korisnik
            if (review.getUser().getId() == userId) {
                holder.actionButton.setVisibility(View.VISIBLE);
                holder.actionButton.setText("Delete");
                holder.actionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.red)));
                holder.actionButton.setOnClickListener(v -> activity.deleteReviewById(review.getId(), position));
            } else {
                holder.actionButton.setVisibility(View.GONE);
            }
        } else if (userRole.equals("OWNER")) {
            // OWNER - prikazivanje dugmeta za prijavljivanje recenzije
            holder.actionButton.setVisibility(View.VISIBLE);
            holder.actionButton.setText("Report");
            holder.actionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.red)));
            holder.actionButton.setOnClickListener(v -> activity.reportReviewById(review.getId(), position));
        } else if (userRole.equals("ADMIN")) {
            // ADMIN - prikazivanje dugmeta za odobravanje recenzije
            holder.actionButton.setVisibility(View.VISIBLE);
            holder.actionButton.setText("Approve");
            holder.actionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.green)));
            //holder.actionButton.setOnClickListener(v -> activity.approveReviewById(review.getId(), position));
        } else {
            holder.actionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewSender, reviewComment, reviewDateTime;
        RatingBar reviewRating;  // Promena sa TextView na RatingBar
        Button actionButton;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewSender = itemView.findViewById(R.id.reviewSender);
            reviewComment = itemView.findViewById(R.id.reviewComment);
            reviewRating = itemView.findViewById(R.id.reviewRating);  // Inicijalizacija RatingBar
            reviewDateTime = itemView.findViewById(R.id.reviewDateTime);
            actionButton = itemView.findViewById(R.id.actionButton);
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


