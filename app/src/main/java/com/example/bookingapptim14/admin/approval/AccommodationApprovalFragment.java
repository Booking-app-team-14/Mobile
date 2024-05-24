package com.example.bookingapptim14.admin.approval;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.AccommodationRequest;

import java.util.ArrayList;
import java.util.List;

public class AccommodationApprovalFragment extends Fragment implements AccommodationApprovalAdapter.OnAccommodationApprovalListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationRequestsRecyclerView;
    private AccommodationApprovalAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_approval, container, false);

        accommodationRequestsRecyclerView = view.findViewById(R.id.accommodationRequestsRecyclerView);
        adapter = new AccommodationApprovalAdapter(new ArrayList<>(), this);
        accommodationRequestsRecyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = adapter.getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(accommodationRequestsRecyclerView);
        accommodationRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAccommodationRequests();

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
                        accommodationRequestsRecyclerView.smoothScrollBy(0, 1200);
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

    private void fetchAccommodationRequests() {
        // TODO: fetch data

        GlobalData data = GlobalData.getInstance();
        List<AccommodationRequest> testRequests = data.getAccommodationRequest();

        adapter.setAccommodationRequests(testRequests);

    }

    @Override
    public void onAccommodationApproved(AccommodationRequest request) {
        if (request.getAccommodationId() == 15L){
            GlobalData data = GlobalData.getInstance();
            data.addAccommodation(request);
        }
        // TODO: Handle approval logic here
        adapter.removeItem(request);
        Log.d("AccommodationApproval", "Approved");
    }

    @Override
    public void onAccommodationRejected(AccommodationRequest request) {
        // TODO: Handle rejection logic here
        adapter.removeItem(request);
        Log.d("AccommodationApproval", "Rejected");
    }

    @Override
    public void onAccommodationDetailsRequested(AccommodationRequest request) {
        // TODO: Open accommodation details fragment here
        Log.d("AccommodationApproval", "Details");
    }

}

