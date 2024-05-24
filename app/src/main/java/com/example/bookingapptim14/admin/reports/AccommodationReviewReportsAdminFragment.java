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
import com.example.bookingapptim14.Adapters.AdminUserReportsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;

import java.util.ArrayList;
import java.util.List;

public class AccommodationReviewReportsAdminFragment extends Fragment implements AdminAccommodationReviewReportsAdapter.OnReportListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView userReportsRecyclerView;
    private AdminAccommodationReviewReportsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_review_reports_admin, container, false);
        userReportsRecyclerView = view.findViewById(R.id.adminAccommodationReviewReportsRecyclerView);
        adapter = new AdminAccommodationReviewReportsAdapter(new ArrayList<>(), this);
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

    // GET api/reviewReports -> AccommodationReviewReportsDTO

    // Convert to AccommodationReviewReportsData

    // GET /accommodationReviews/{reviewId} -> AccommodationReviewDTO

    // GET users/{id}/image-type-username -> userUsername + " | " + userProfilePictureType + " | " + userProfilePictureBytes

    // GET accommodations/{accommodationId}/nameAndTypeAndBytes -> accommodationName + " | " + accommodationType + " | " + accommodationImageBytes

    public void fetchAccommodationReviewReports() {
        // TODO: fetch data
        // Convert to AccommodationReviewReportsData

        GlobalData data = GlobalData.getInstance();
        List<AccommodationReviewReportsData> testReports = data.getAccommodationReviewReports();

        adapter.setAccommodationReviewReports(testReports);
    }

    // Remove accommodation review: DELETE api/reviewReports/accommodationReviews/{reviewReportId}
    @Override
    public void onReviewRemoved(AccommodationReviewReportsData report) {
        // TODO: Handle user block logic here

        adapter.removeItem(report);
    }

    // Dismiss accommodation review report: DELETE api/reviewReports/{reviewReportId}
    @Override
    public void onReviewReportDismissed(AccommodationReviewReportsData report) {
        // TODO: Handle dismiss logic here

        adapter.removeItem(report);
    }

    @Override
    public void onDetailsRequested(AccommodationReviewReportsData report) {
        // TODO: Open details fragment here
    }

}