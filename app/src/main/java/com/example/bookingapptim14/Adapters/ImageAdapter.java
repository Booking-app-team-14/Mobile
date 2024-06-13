package com.example.bookingapptim14.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Bitmap> imageBitmaps;
    private Context context;

    public ImageAdapter(ArrayList<Bitmap> imageBitmaps, Context context) {
        this.imageBitmaps = imageBitmaps;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Bitmap bitmap = imageBitmaps.get(position);
        holder.imageView.setImageBitmap(bitmap);
        holder.removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmaps.size() == 1) {
                    Toast.makeText(context, "You must have at least one image", Toast.LENGTH_SHORT).show();
                    return;
                }
                removeItem(bitmap);
            }
        });
    }

    public void removeItem(Bitmap image) {
        int position = imageBitmaps.indexOf(image);
        if (position == -1) {
            return;
        }
        imageBitmaps.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return imageBitmaps.size();
    }

    public List<Bitmap> getImages() {
        return imageBitmaps;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button removeImageButton;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            removeImageButton = itemView.findViewById(R.id.remove_image_button);
        }
    }
}