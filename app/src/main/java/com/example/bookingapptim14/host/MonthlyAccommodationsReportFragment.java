package com.example.bookingapptim14.host;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.MonthlyAccommodationReportAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.MonthlyAccommodationReport;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonthlyAccommodationsReportFragment extends Fragment {

    private Long accommodationId;

    private static final String TAG = "MonthlyAccommodationsReportFragment";
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationsRecyclerView;
    private MonthlyAccommodationReportAdapter adapter;

    private String jwtToken;

    private Long userId;
    private List<MonthlyAccommodationReport> reports = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            accommodationId = getArguments().getLong("accommodationId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_montly_report, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        Spinner yearSpinner = view.findViewById(R.id.yearSpinner);
        List<String> years = new ArrayList<>();
        for (int i = 2000; i <= 2030; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        Button showReportsButton = view.findViewById(R.id.showReportsButton);
        BarChart reservationsBarChart = view.findViewById(R.id.reservationsBarChart);
        Button pdfButton = view.findViewById(R.id.generatePdfButton);

        pdfButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Generate PDF based on the date range
                generatePdf(getContext(), reports);
            } else {
                requestStoragePermission();
            }
        });

        showReportsButton.setOnClickListener(v -> {
            String selectedYear = yearSpinner.getSelectedItem().toString();
            // Fetch data for the selected year
            fetchMonthlyReservations(selectedYear, reservationsBarChart);
        });

        accommodationsRecyclerView = view.findViewById(R.id.hostAccommodationsRecyclerView);
        adapter = new MonthlyAccommodationReportAdapter(new ArrayList<>());
        accommodationsRecyclerView.setAdapter(adapter);
        accommodationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensor != null) {
            proximityEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (event.values[0] < proximitySensor.getMaximumRange()) {
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

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", this.getContext().getPackageName())));
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 1);
                }
            } else {
                generatePdf(getContext(), reports);
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                generatePdf(getContext(), reports);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePdf(getContext(), reports);
            } else {
                Toast.makeText(getContext(), "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    generatePdf(getContext(), reports);
                } else {
                    Toast.makeText(getContext(), "Permission denied to manage all files", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void updateBarChart(BarChart barChart, int[] monthlyReservations) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < monthlyReservations.length; i++) {
            entries.add(new BarEntry(i, monthlyReservations[i]));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Reservations");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh
    }

    private void fetchMonthlyReservations(String year, BarChart barChart) {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodation-reports/" + accommodationId + "/monthly-report");
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
                    Type mapType = new TypeToken<Map<String, MonthlyAccommodationReport>>(){}.getType();

                    Map<String, MonthlyAccommodationReport> monthlyAccommodationReportMap = gson.fromJson(content.toString(), mapType);

                    List<MonthlyAccommodationReport> fetchedReports = new ArrayList<>();
                    for (Map.Entry<String, MonthlyAccommodationReport> entry : monthlyAccommodationReportMap.entrySet()) {
                        fetchedReports.add(entry.getValue());
                    }

                    getActivity().runOnUiThread(() -> {
                        adapter.setReports(fetchedReports);
                        reports = fetchedReports;

                        ArrayList<Integer> numbersList = new ArrayList<>();
                        for (MonthlyAccommodationReport report : fetchedReports) {
                            numbersList.add(report.getNumberOfReservations());
                        }

                        int[] numbersArray = new int[numbersList.size()];
                        for (int i = 0; i < numbersList.size(); i++) {
                            numbersArray[i] = numbersList.get(i);
                        }

                        updateBarChart(barChart, numbersArray);
                    });
                } else {
                    System.out.println("GET request failed!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void generatePdf(Context context, List<MonthlyAccommodationReport> reports) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        int pageNumber = 1;
        int itemsPerPage = 5;
        int totalPages = (int) Math.ceil((double) reports.size() / itemsPerPage);

        for (int i = 0; i < totalPages; i++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, pageNumber).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            int yPosition = 40;

            paint.setTextSize(18f);
            canvas.drawText("Monthly Report", 40, yPosition, paint);
            yPosition += 40;

            paint.setTextSize(14f);
            for (int j = 0; j < itemsPerPage; j++) {
                int itemIndex = i * itemsPerPage + j;
                if (itemIndex >= reports.size()) break;
                MonthlyAccommodationReport report = reports.get(itemIndex);

                String reportText = String.format("Month: %s\nTotal Profit: %.2f\nReservations: %d\n\n\n",
                        report.getMonth(), report.getTotalProfit(), report.getNumberOfReservations());

                for (String line : reportText.split("\n")) {
                    canvas.drawText(line, 40, yPosition, paint);
                    yPosition += 20;
                }
                yPosition += 10;  // Add some space between reports
            }

            pdfDocument.finishPage(page);
            pageNumber++;
        }

        // Save the PDF to external storage
        String fileName = "MonthlyReport" + accommodationId + ".pdf";
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(pdfFile));
            Toast.makeText(context, "PDF downloaded successfully to " + pdfFile.getName(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error downloading PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            pdfDocument.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(proximityEventListener, proximitySensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (proximitySensor != null) {
            sensorManager.unregisterListener(proximityEventListener);
        }
    }
}
