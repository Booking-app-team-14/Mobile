package com.example.bookingapptim14.host;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.AccommodationReportsAdapter;
import com.example.bookingapptim14.Adapters.LocalDateDeserializer;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationReportDTO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

public class AccommodationReportsFragment extends Fragment {

    private static final String TAG = "AccommodationReportsFragment";
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationsRecyclerView;
    private AccommodationReportsAdapter adapter;
    private PieChart pieChart;
    private List<AccommodationReportDTO> reports = new ArrayList<>();
    private Long userId;
    private String jwtToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_report, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);

        EditText startDateEditText = view.findViewById(R.id.startDateEditText);
        EditText endDateEditText = view.findViewById(R.id.endDateEditText);
        Button showReportsButton = view.findViewById(R.id.showReportsButton);
        Button generatePdfButton = view.findViewById(R.id.generatePdfButton);

        // Date picker logic
        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));

        showReportsButton.setOnClickListener(v -> {
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();

            // Fetch and display the reports based on the date range
            fetchReports(startDate, endDate);
        });

        generatePdfButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Generate PDF based on the date range
                generatePdf(getContext(), reports);
            } else {
                requestStoragePermission();
            }
        });

        pieChart = view.findViewById(R.id.profitPieChart);
        accommodationsRecyclerView = view.findViewById(R.id.hostAccommodationsRecyclerView);
        adapter = new AccommodationReportsAdapter(new ArrayList<>());
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

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editText.setText(date);
        }, year, month, day);
        datePickerDialog.show();
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

    private void fetchReports(String startDate, String endDate) {
        // convert startDate to local date string
        String[] startDateParts = startDate.split("/");
        int startDay = Integer.parseInt(startDateParts[0]);
        int startMonth = Integer.parseInt(startDateParts[1]);
        int startYear = Integer.parseInt(startDateParts[2]);
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(startYear, startMonth - 1, startDay);
        ZonedDateTime startZonedDateTime = startCalendar.getTime().toInstant().atZone(ZoneId.systemDefault());
        String startDateFormatted = startZonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);

        // convert endDate to local date string
        String[] endDateParts = endDate.split("/");
        int endDay = Integer.parseInt(endDateParts[0]);
        int endMonth = Integer.parseInt(endDateParts[1]);
        int endYear = Integer.parseInt(endDateParts[2]);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(endYear, endMonth - 1, endDay);
        ZonedDateTime endZonedDateTime = endCalendar.getTime().toInstant().atZone(ZoneId.systemDefault());
        String endDateFormatted = endZonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/accommodation-reports/" + userId + "?startDate=" + startDateFormatted + "&endDate=" + endDateFormatted);
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

                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                            .create();
                    Type listType = new TypeToken<List<AccommodationReportDTO>>(){}.getType();
                    List<AccommodationReportDTO> fetchedReports = gson.fromJson(content.toString(), listType);

                    getActivity().runOnUiThread(() -> {
                        adapter.setAccommodations(fetchedReports);
                        reports = fetchedReports;
                        updatePieChart(fetchedReports);
                    });
                } else {
                    System.out.println("GET request failed!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updatePieChart(List<AccommodationReportDTO> reports) {
        if (reports.isEmpty()) {
            // Handle case when there are no reports available
            return;
        }

        Map<String, Float> profitData = new HashMap<>();
        for (AccommodationReportDTO accommodationReport : reports) {
            profitData.put(accommodationReport.getAccommodationName(), (float) accommodationReport.getTotalProfit());
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : profitData.entrySet()) {
            String accommodationName = entry.getKey();
            Float profit = entry.getValue();
            entries.add(new PieEntry(profit, accommodationName)); // Use accommodationName as label
        }

        PieDataSet dataSet = new PieDataSet(entries, "Accommodation Profits");
        dataSet.setColors(Color.rgb(255, 102, 0), Color.rgb(0, 204, 102), Color.rgb(0, 204, 255));
        PieData dataPie = new PieData(dataSet);
        dataPie.setValueFormatter(new PercentFormatter());
        dataPie.setValueTextSize(11f);
        dataPie.setValueTextColor(Color.WHITE);

        pieChart.setData(dataPie);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.animateY(1400);
    }

    public void generatePdf(Context context, List<AccommodationReportDTO> reports) {
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
            canvas.drawText("Accommodation Report", 40, yPosition, paint);
            yPosition += 40;

            paint.setTextSize(14f);
            for (int j = 0; j < itemsPerPage; j++) {
                int itemIndex = i * itemsPerPage + j;
                if (itemIndex >= reports.size()) break;
                AccommodationReportDTO report = reports.get(itemIndex);

                String reportText = String.format("Name: %s\nType: %s\nRating: %.1f\nMin guests: %d\n" +
                                "Max Guests: %d\nPrice: %.2f\nTotal Profit: %.2f\nReservations: %d\n\n\n",
                        report.getAccommodationName(), report.getType().toString(), report.getRating(),
                        report.getMinNumberOfGuests(), report.getMaxNumberOfGuests(), report.getPricePerNight(),
                        report.getTotalProfit(), report.getNumberOfReservations());

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
        String fileName = "AccommodationReports" + userId + ".pdf";
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