package com.example.bookingapptim14.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.admin.approval.AccommodationApprovalViewHolder;
import com.example.bookingapptim14.models.AccommodationRequest;

import java.util.List;

public class AccommodationApprovalAdapter extends RecyclerView.Adapter<AccommodationApprovalViewHolder> {

    private List<AccommodationRequest> accommodationRequests;
    private OnAccommodationApprovalListener listener;

    public AccommodationApprovalAdapter(List<AccommodationRequest> accommodationRequests, OnAccommodationApprovalListener listener) {
        this.accommodationRequests = accommodationRequests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccommodationApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accommodation_request, parent, false);
        return new AccommodationApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccommodationApprovalViewHolder holder, int position) {
        AccommodationRequest request = accommodationRequests.get(position);
        holder.bind(request);

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationRejected(request);
            }
        });

        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationApproved(request);
            }
        });

        holder.viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccommodationDetailsRequested(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accommodationRequests.size();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            AccommodationRequest request = accommodationRequests.get(position);

            if (direction == ItemTouchHelper.LEFT) {
                listener.onAccommodationRejected(request);
            } else {
                listener.onAccommodationApproved(request);
            }
        }
    };

    public ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(itemTouchHelperCallback);
    }

    public void setAccommodationRequests(List<AccommodationRequest> accommodationRequests) {
        this.accommodationRequests = accommodationRequests;
        notifyDataSetChanged();
    }

    public void removeItem(AccommodationRequest request) {
        int position = accommodationRequests.indexOf(request);
        if (position == -1) {
            return;
        }
        accommodationRequests.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnAccommodationApprovalListener {
        void onAccommodationApproved(AccommodationRequest request);
        void onAccommodationRejected(AccommodationRequest request);
        void onAccommodationDetailsRequested(AccommodationRequest request);
    }

}
