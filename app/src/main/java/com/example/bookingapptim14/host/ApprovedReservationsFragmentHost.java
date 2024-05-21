package com.example.bookingapptim14.host;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.ApprovedReservationsAdapter;
import com.example.bookingapptim14.Adapters.HostAccommodationsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;

import java.util.ArrayList;
import java.util.List;

public class ApprovedReservationsFragmentHost extends Fragment {

    private RecyclerView reservationsRecyclerView;
    private ApprovedReservationsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved_reservations_host, container, false);

        reservationsRecyclerView = view.findViewById(R.id.approvedReservationsRecyclerView);
        adapter = new ApprovedReservationsAdapter(new ArrayList<>());
        reservationsRecyclerView.setAdapter(adapter);
        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchReservations();

        return view;
    }

    // /api /requests/owner/{username} -> ReservationReqeustDTO
    // .requestStatus == "SENT"

    // convert to ApprovedReservationData

    // /users/{id}/usernameAndNumberOfCancellations -> vraca string username + " | " + numberOfCancellationsString

    private void fetchReservations() {
        // TODO: fetch data

        // process all the DTOs and make ApprovedReservationData

        // For now, use test data
        GlobalData data = GlobalData.getInstance();
        List<ApprovedReservationData> testReservations = data.getApprovedReservations();

        adapter.setReservations(testReservations);
    }

}