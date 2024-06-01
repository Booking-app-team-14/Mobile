package com.example.bookingapptim14.host;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingapptim14.Adapters.AccommodationReportsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationReportDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccommodationReportsFragment extends Fragment {
    private static final String TAG = "AccommodationReportsFragment";
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximityEventListener;
    private RecyclerView accommodationsRecyclerView;
    private AccommodationReportsAdapter adapter;

    private List<AccommodationReportDTO> reports = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accommodation_report, container, false);

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
            reports = fetchReports(startDate, endDate);
        });

        generatePdfButton.setOnClickListener(v -> {
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Generate PDF based on the date range
                generatePdf(this.getContext(), reports);
            } else {
                requestStoragePermission();
            }
        });

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
        String fileName = "AccommodationReports.pdf";
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            pdfDocument.writeTo(fos);
            Toast.makeText(context, "PDF saved successfully to " + pdfFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

            // Notify media scanner
            MediaScannerConnection.scanFile(context, new String[]{pdfFile.getAbsolutePath()}, null, (path, uri) -> {
                Log.i(TAG, "Scanned " + path + ":");
                Log.i(TAG, "-> uri=" + uri);
            });
        } catch (IOException e) {
            Log.e(TAG, "Error saving PDF", e);
            Toast.makeText(context, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            pdfDocument.close();
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            generatePdf(this.getContext(), reports);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePdf(this.getContext(), reports);
            } else {
                Toast.makeText(getContext(), "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDatePickerDialog(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), (view, year1, month1, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editText.setText(date);
        }, year, month, day);
        datePickerDialog.show();
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

    private List<AccommodationReportDTO> fetchReports(String startDate, String endDate) {
        //TODO endpoint!

        // For now, use test data
        GlobalData data = GlobalData.getInstance();
        List<AccommodationReportDTO> testAccommodations = data.getAccommodationReports();
        Map<String, Double> profitData = new HashMap<>();
        for (AccommodationReportDTO accommodationReport:testAccommodations){
            profitData.put(accommodationReport.getAccommodationName(),accommodationReport.getTotalProfit());
        }

        adapter.setAccommodations(testAccommodations);

        return testAccommodations;
    }
}
