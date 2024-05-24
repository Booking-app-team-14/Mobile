package com.example.bookingapptim14.admin.reports;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AdminAccommodationReviewReportsAdapter;
import com.example.bookingapptim14.Adapters.AdminOwnerReviewReportAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;

import java.util.ArrayList;
import java.util.List;

public class OwnerReviewReportsAdminFragment extends Fragment implements AdminOwnerReviewReportAdapter.OnReportListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
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

        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor != null) {
            proximityEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if(event.values[0] < proximitySensor.getMaximumRange()) {
                        // Detected something nearby
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        userReportsRecyclerView.smoothScrollBy(0, 1200);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
        } else {
            Toast.makeText(getContext(), "This device has no proximity sensor", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(proximityEventListener, proximitySensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximityEventListener);
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