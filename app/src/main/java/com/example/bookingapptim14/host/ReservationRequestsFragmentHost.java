package com.example.bookingapptim14.host;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bookingapptim14.Adapters.ReservationRequestsAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ReservationRequestDTO;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRequestsFragmentHost extends Fragment {
    private Button btnDateRangePicker;
    private TextView textViewDateRange;
    private EditText editTextSearch;
    private RadioGroup radioGroupStatus;
    private RecyclerView recyclerViewRequests;
    private ReservationRequestsAdapter requestsAdapter;
    private List<ApprovedReservationData> requestList = new ArrayList<>();
    private List<ApprovedReservationData> filteredList = new ArrayList<>();
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;
    private String jwtToken;
    private Long userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");
        userId = sharedPreferences.getLong("userId", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_requests_host, container, false);

        initializeViews(view);
        setupListeners();
        fetchRequests();

        return view;
    }

    private void initializeViews(View view) {
        btnDateRangePicker = view.findViewById(R.id.buttonDateRangeReservationRequests);
        textViewDateRange = view.findViewById(R.id.textViewDateRange);
        editTextSearch = view.findViewById(R.id.editTextSearchReservationRequests);
        radioGroupStatus = view.findViewById(R.id.radioGroupReservationRequestsStatus);
        recyclerViewRequests = view.findViewById(R.id.recyclerViewRequests);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        requestsAdapter = new ReservationRequestsAdapter(filteredList);
        recyclerViewRequests.setAdapter(requestsAdapter);
    }

    private void setupListeners() {
        btnDateRangePicker.setOnClickListener(v -> openDateRangePicker());
        radioGroupStatus.setOnCheckedChangeListener((group, checkedId) -> filterRequests());
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterRequests();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void openDateRangePicker() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Dates");

        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

        picker.addOnPositiveButtonClickListener(selection -> {
            selectedStartDate = Instant.ofEpochMilli(selection.first).atZone(ZoneId.systemDefault()).toLocalDate();
            selectedEndDate = Instant.ofEpochMilli(selection.second).atZone(ZoneId.systemDefault()).toLocalDate();

            String dateRange = String.format(
                    "Check-in: %s\nCheck-out: %s",
                    android.text.format.DateFormat.format("dd/MM/yyyy", selection.first),
                    android.text.format.DateFormat.format("dd/MM/yyyy", selection.second)
            );
            textViewDateRange.setText(dateRange);
            filterRequests();
        });

        picker.show(getChildFragmentManager(), picker.toString());
    }

    private void fetchRequests() {
        new Thread(() -> {
            try {
                URL url = new URL(BuildConfig.IP_ADDR + "/api/requests/owner/" + userId);
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

                    List<ApprovedReservationData> approvedReservations = new ArrayList<>();

                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<ReservationRequestDTO>>(){}.getType();
                    List<ReservationRequestDTO> reservationRequestDTOs = gson.fromJson(content.toString(), listType);

                    for (ReservationRequestDTO dto : reservationRequestDTOs) {
                        URL url2 = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getGuestId() + "/usernameAndNumberOfCancellations");
                        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                        conn2.setRequestMethod("GET");
                        conn2.setDoInput(true);
                        conn2.setRequestProperty("Authorization", "Bearer " + jwtToken);

                        int responseCode2 = conn2.getResponseCode();
                        if (responseCode2 == HttpURLConnection.HTTP_OK) {
                            BufferedReader in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                            String inputLine2;
                            StringBuilder content2 = new StringBuilder();
                            while ((inputLine2 = in2.readLine()) != null) {
                                content2.append(inputLine2);
                            }
                            in2.close();
                            conn2.disconnect();

                            String[] usernameAndNumberOfCancellations = content2.toString().split(" \\| ");
                            String username = usernameAndNumberOfCancellations[0];
                            String numberOfCancellationsString = usernameAndNumberOfCancellations[1];

                            ApprovedReservationData approvedReservation = new ApprovedReservationData(dto, dto.getDateRequested(), username, numberOfCancellationsString);
                            approvedReservations.add(approvedReservation);
                        }
                    }

                    getActivity().runOnUiThread(() -> {
                        requestList = approvedReservations;
                        filteredList = new ArrayList<>(requestList);
                        requestsAdapter.setReservations(filteredList);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @SuppressLint("DefaultLocale")
    private void filterRequests() {
        String query = editTextSearch.getText().toString().toLowerCase();
        int selectedStatusId = radioGroupStatus.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = radioGroupStatus.findViewById(selectedStatusId);
        String selectedStatus = selectedRadioButton.getText().toString();

        filteredList = requestList.stream()
                .filter(request -> (query.isEmpty() || request.getGuestUsername().toLowerCase().contains(query))
                        && (selectedStatus.equals("All") || request.getRequestStatus().equalsIgnoreCase(selectedStatus))
                        && (selectedStartDate == null || (request.getStartDate().compareTo(selectedStartDate) >= 0 && request.getEndDate().compareTo(selectedEndDate) <= 0)))
                .collect(Collectors.toList());

        requestsAdapter.setReservations(filteredList);
    }
}
