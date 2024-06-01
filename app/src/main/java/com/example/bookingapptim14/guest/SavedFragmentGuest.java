package com.example.bookingapptim14.guest;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Intent;
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

import com.example.bookingapptim14.Adapters.FavouritesAdapter;
import com.example.bookingapptim14.Adapters.HostAccommodationsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.host.AccomodationDetailsActivityHost;
import com.example.bookingapptim14.host.CreateAccommodationScreen;
import com.example.bookingapptim14.host.UpdateAccommodationScreen;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.ArrayList;
import java.util.List;

public class SavedFragmentGuest extends Fragment implements FavouritesAdapter.OnAccommodationListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationsRecyclerView;
    private FavouritesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_guest, container, false);



        accommodationsRecyclerView = view.findViewById(R.id.savedAccommodationRecycleView);
        adapter = new FavouritesAdapter(new ArrayList<>(), this);
        accommodationsRecyclerView.setAdapter(adapter);
        accommodationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAccommodations();

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
                        accommodationsRecyclerView.smoothScrollBy(0, 1200);
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

    private void fetchAccommodations() {
        // TODO: fetch data

        // For now, use test data
        GlobalData data = GlobalData.getInstance();
        List<OwnersAccommodationDTO> testAccommodations = data.getOwnersAccommodations();

        adapter.setAccommodations(testAccommodations);
    }


    @Override
    public void onAccommodationDetailsRequested(OwnersAccommodationDTO accommodation) {
        // TODO: Open accommodation details fragment here
        Long accommodationId = accommodation.getId();

        // trenutno otvara template accommodation details
        Intent intent = new Intent(getActivity(), AccommodationDetailsActivityGuest.class);
        startActivity(intent);
    }
}