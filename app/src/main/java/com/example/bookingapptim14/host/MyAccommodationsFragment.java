package com.example.bookingapptim14.host;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.HostAccommodationsAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ReservationRequestDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyAccommodationsFragment extends Fragment implements HostAccommodationsAdapter.OnAccommodationListener {

    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationsRecyclerView;
    private HostAccommodationsAdapter adapter;
    private String jwtToken;
    private long userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_accommodations, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        view.findViewById(R.id.addAccommodationButton).setOnClickListener(v -> {
            Intent createAccommodationIntent = new Intent(getActivity(), CreateAccommodationScreen.class);
            startActivity(createAccommodationIntent);
        });

        view.findViewById(R.id.generateReportsButton).setOnClickListener(v -> {
            Fragment fragment = new AccommodationReportsFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        });

        accommodationsRecyclerView = view.findViewById(R.id.hostAccommodationsRecyclerView);
        adapter = new HostAccommodationsAdapter(new ArrayList<>(), this);
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
        // GET api/accommodations/owners/{ownerId} -> List<OwnersAccommodationDTO>
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodations/owners/" + userId);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder content = new StringBuilder();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                        conn.disconnect();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<OwnersAccommodationDTO>>(){}.getType();
                        List<OwnersAccommodationDTO> hostAccommodations = gson.fromJson(content.toString(), listType);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setAccommodations(hostAccommodations);
                            }
                        });
                    } else {
                        System.out.println("GET request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onMonthlyReportsRequested(OwnersAccommodationDTO accommodation) {
        Long accommodationId = accommodation.getId();

        Fragment fragment = new MonthlyAccommodationsReportFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("accommodationId", accommodationId);
        fragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAccommodationDetailsRequested(OwnersAccommodationDTO accommodation) {
        Long accommodationId = accommodation.getId();
        Intent intent = new Intent(getActivity(), AccomodationDetailsActivityHost.class);
        intent.putExtra("accommodationId", accommodationId);
        startActivity(intent);
    }

    @Override
    public void onAccommodationUpdate(OwnersAccommodationDTO accommodation) {
        Long accommodationId = accommodation.getId();

//        Fragment fragment = new AccommodationDetailsUpdateFragment();
//        Fragment fragment = new MyAccommodationsFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

        Intent updateAccommodationIntent = new Intent(getActivity(), UpdateAccommodationScreen.class);
        updateAccommodationIntent.putExtra("accommodationId", accommodationId);
        startActivity(updateAccommodationIntent);

    }
}