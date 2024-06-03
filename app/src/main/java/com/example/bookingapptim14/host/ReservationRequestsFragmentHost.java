package com.example.bookingapptim14.host;

import android.annotation.SuppressLint;
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
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.enums.ConfirmationMethod;
import com.example.bookingapptim14.models.ReservationRequest;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;
import com.google.android.material.datepicker.MaterialDatePicker;

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
    public ReservationRequestsAdapter requestsAdapter;
    private List<ApprovedReservationData> requestList;
    private List<ApprovedReservationData> filteredList;
    private ConfirmationMethod confirmationMethod;
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_requests_host, container, false);
        btnDateRangePicker = view.findViewById(R.id.buttonDateRangeReservationRequests);
        textViewDateRange = view.findViewById(R.id.textViewDateRange);
        editTextSearch = view.findViewById(R.id.editTextSearchReservationRequests);
        radioGroupStatus = view.findViewById(R.id.radioGroupReservationRequestsStatus);
        recyclerViewRequests = view.findViewById(R.id.recyclerViewRequests);
        recyclerViewRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        requestsAdapter = new ReservationRequestsAdapter(filteredList);
        recyclerViewRequests.setAdapter(requestsAdapter);

        fetchRequests();
        filteredList = new ArrayList<>(requestList);
        btnDateRangePicker.setOnClickListener(v -> openDateRangePicker());

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

        radioGroupStatus.setOnCheckedChangeListener((group, checkedId) -> filterRequests());

        return view;
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
        // TODO: fetch data

        GlobalData data = GlobalData.getInstance();
        requestList = data.getApprovedReservations();

        requestsAdapter.setReservations(requestList);
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