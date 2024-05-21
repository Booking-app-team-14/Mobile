package com.example.bookingapptim14.guest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        return view;
    }

    // /api /requests/owner/{username} -> ReservationReqeustDTO
    // .requestStatus == "SENT"

    // convert to ApprovedReservationGuestData

    // /accommodations/{id}/cancellationDeadline -> vraca string cancellationDeadline

    // /users/{id}/usernameAndNumberOfCancellations -> vraca string username + " | " + numberOfCancellationsString

    private void fetchReservations() {
        // TODO: fetch data

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