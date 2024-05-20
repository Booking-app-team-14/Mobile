package com.example.bookingapptim14.host;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.HostAccommodationsAdapter;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.Accommodation;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;

import java.util.ArrayList;
import java.util.List;

public class MyAccommodationsFragment extends Fragment implements HostAccommodationsAdapter.OnAccommodationListener {
    private RecyclerView accommodationsRecyclerView;
    private HostAccommodationsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_accommodations, container, false);

        accommodationsRecyclerView = view.findViewById(R.id.hostAccommodationsRecyclerView);
        adapter = new HostAccommodationsAdapter(new ArrayList<>(), this);
        accommodationsRecyclerView.setAdapter(adapter);
        accommodationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchAccommodations();

        return view;
    }

    private void fetchAccommodations() {
        // TODO: fetch data

        // For now, use test data
        GlobalData data = GlobalData.getInstance();
        List<OwnersAccommodationDTO> testAccommodations = data.getOwnersAccommodations();

        adapter.setAccommodations(testAccommodations);
    }

    @Override
    public void onAccommodationDetailsRequested(OwnersAccommodationDTO request) {
        // TODO: Open accommodation details fragment here
    }

    @Override
    public void onAccommodationUpdate(OwnersAccommodationDTO accommodation) {
        // TODO: Open accommodation update fragment here
    }
}