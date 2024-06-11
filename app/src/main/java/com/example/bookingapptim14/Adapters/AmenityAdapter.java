package com.example.bookingapptim14.Adapters;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.CheckableAmenity;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class AmenityAdapter extends RecyclerView.Adapter<AmenityAdapter.ViewHolder> {
    List<CheckableAmenity> checkableAmenities;

    public AmenityAdapter(List<CheckableAmenity> amenities) {
        checkableAmenities = amenities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckableAmenity amenity = checkableAmenities.get(position);
        holder.amenityCheckbox.setChecked(amenity.isChecked());
        holder.amenityName.setText(amenity.getName());
        String base64Image = amenity.getIconBytes();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.getDecoder().decode(base64Image);
            holder.amenityIcon.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }

        holder.amenityCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            amenity.setChecked(isChecked);
        });
    }

    public void checkAmenity(Long amenityId) {
        for (CheckableAmenity amenity : checkableAmenities) {
            if (amenity.getId().equals(amenityId)) {
                amenity.setChecked(true);
                notifyItemChanged(checkableAmenities.indexOf(amenity));
                return;
            }
        }
    }

    public Set<Long> getCheckedAmenitiesIds() {
        Set<Long> checkedAmenitiesIds = new HashSet<>();
        for (CheckableAmenity amenity : checkableAmenities) {
            if (amenity.isChecked()) {
                checkedAmenitiesIds.add(amenity.getId());
            }
        }
        return checkedAmenitiesIds;
    }

    @Override
    public int getItemCount() {
        return checkableAmenities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox amenityCheckbox;
        CircleImageView amenityIcon;
        TextView amenityName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amenityCheckbox = itemView.findViewById(R.id.amenity_checkbox);
            amenityIcon = itemView.findViewById(R.id.amenity_icon);
            amenityName = itemView.findViewById(R.id.amenity_name);
        }
    }
}