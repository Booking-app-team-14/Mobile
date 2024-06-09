package com.example.bookingapptim14.guest;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.ApprovedReservationsAdapter;
import com.example.bookingapptim14.Adapters.ApprovedReservationsGuestAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;

import java.util.ArrayList;
import java.util.List;

public class ApprovedReservationsFragmentGuest extends Fragment implements ApprovedReservationsGuestAdapter.OnReservationListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView reservationsRecyclerView;
    private ApprovedReservationsGuestAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved_reservations_guest, container, false);

        reservationsRecyclerView = view.findViewById(R.id.approvedReservationsGuestRecyclerView);
        adapter = new ApprovedReservationsGuestAdapter(new ArrayList<>(), this);
        reservationsRecyclerView.setAdapter(adapter);
        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchReservations();

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
                        reservationsRecyclerView.smoothScrollBy(0, 1200);
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

    // /api /requests/owner/{username} -> ReservationReqeustDTO
    // .requestStatus == "SENT"

    // convert to ApprovedReservationGuestData

    // /accommodations/{id}/cancellationDeadline -> vraca string cancellationDeadline

    // /users/{id}/usernameAndNumberOfCancellations -> vraca string username + " | " + numberOfCancellationsString

    private void fetchReservations() {
        // TODO: [VUK] fetch data

        // process all the DTOs and make ApprovedReservationGuestData

        // For now, use test data
        GlobalData data = GlobalData.getInstance();
        List<ApprovedReservationGuestData> testReservations = new ArrayList<ApprovedReservationGuestData>(data.getApprovedGuestReservations());

        adapter.setReservations(testReservations);
    }

    @Override
    public void onReservationCancelled(ApprovedReservationGuestData reservation) {
        // TODO: cancel reservation

        adapter.removeItem(reservation);
    }

}