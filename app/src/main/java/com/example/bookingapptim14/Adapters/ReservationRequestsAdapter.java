package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.models.ReservationRequest;

import java.util.List;
/*
public class ReservationRequestsAdapter extends RecyclerView.Adapter<ReservationRequestsAdapter.RequestViewHolder> {
/*
    private List<ReservationRequest> requestList;
    private ConfirmationMethod confirmationMethod;

    public ReservationRequestsAdapter(List<ReservationRequest> requestList, ConfirmationMethod confirmationMethod) {
        this.requestList = requestList;
        this.confirmationMethod = confirmationMethod;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_reservations_host, parent, false);
        return new RequestViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        ReservationRequest request = requestList.get(position);

        holder.imageViewUserProfile.setImageResource(request.getUserProfileImage());
        holder.textViewUserName.setText(request.getUserName());

        // Set apartment details data to the views
        holder.imageViewApartment.setImageResource(request.getApartmentImage());
        holder.textViewApartmentName.setText(request.getApartmentName());
        holder.textViewApartmentType.setText(request.getApartmentType());

        holder.textViewStartDate.setText("Start Date: " + request.getStartDate());
        holder.textViewEndDate.setText("End Date: " + request.getEndDate());
        holder.textViewNumGuests.setText("Number of Guests: " + request.getNumGuests());

        // Set dates and guests


        // Handle accept or deny actions for each request
        holder.buttonAccept.setOnClickListener(v -> {
            // Handle accept action for this request
            // ...
        });

        holder.buttonDeny.setOnClickListener(v -> {
            // Handle deny action for this request
            // ...
        });
        // Handle accept or reject actions based on the confirmation method
        if (confirmationMethod == ConfirmationMethod.MANUAL) {
            holder.buttonAccept.setOnClickListener(v -> {
                // Handle accept action for manual confirmation
                // Perform actions like updating database, UI changes, etc.
                // based on the accepted request
            });

            holder.buttonDeny.setOnClickListener(v -> {
                // Handle reject action for manual confirmation
                // Perform actions like updating database, UI changes, etc.
                // based on the rejected request
            });
        } else if (confirmationMethod == ConfirmationMethod.AUTOMATIC) {
            // For automatic confirmation, you might hide or disable these buttons
            holder.buttonAccept.setVisibility(View.GONE);
            holder.buttonDeny.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    static class RequestViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUserProfile;
        TextView textViewUserName;
        ImageView imageViewApartment;
        TextView textViewApartmentName;
        TextView textViewApartmentType;
        TextView textViewDateAndGuests;
        TextView textViewStartDate;
        TextView textViewEndDate;
        TextView textViewNumGuests;
        Button buttonAccept;
        Button buttonDeny;


        RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewUserProfile = itemView.findViewById(R.id.imageViewUserProfile);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            imageViewApartment = itemView.findViewById(R.id.imageViewApartment);
            textViewApartmentName = itemView.findViewById(R.id.textViewApartmentName);
            textViewApartmentType = itemView.findViewById(R.id.textViewApartmentType);
            textViewStartDate = itemView.findViewById(R.id.textViewDateGuests);
            buttonAccept = itemView.findViewById(R.id.buttonAccept);
            buttonDeny = itemView.findViewById(R.id.buttonDeny);
        }
    }
    W

}*/

