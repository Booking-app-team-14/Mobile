package com.example.bookingapptim14;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bookingapptim14.guest.HomeFragmentGuest;
import com.example.bookingapptim14.guest.NotificationsFragmentGuest;
import com.example.bookingapptim14.guest.ProfileFragmentGuest;
import com.example.bookingapptim14.guest.SavedFragmentGuest;

public class UpdateAccountFragment extends Fragment {

    // private User loggedInUser;

    public UpdateAccountFragment() { //User loggedInUser) {
        // TODO: Pass user to fragment
        // this.loggedInUser = loggedInUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);

        Button buttonUpdateDetails = (Button) view.findViewById(R.id.buttonUpdateDetails);
        buttonUpdateDetails.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { updateDetails(); } });

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {
            getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.VISIBLE);
            getActivity().getOnBackPressedDispatcher().onBackPressed();
        }});

        getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.GONE);

        return view;
    }

    private void updateDetails() {
        // TODO: Update account details
        String firstName = ((EditText) getActivity().getWindow().findViewById(R.id.editTextName)).getText().toString();
        String lastName = ((EditText) getActivity().getWindow().findViewById(R.id.editTextSurname)).getText().toString();
        String phoneNumber = ((EditText) getActivity().getWindow().findViewById(R.id.editTextPhone)).getText().toString();
        String address = ((EditText) getActivity().getWindow().findViewById(R.id.editTextAddress)).getText().toString();

        GlobalData gd = GlobalData.getInstance();
        if (!firstName.isEmpty()){
            gd.getLoggedInUser().setFirstName(firstName);
        }
        if (!lastName.isEmpty()){
            gd.getLoggedInUser().setLastName(lastName);
        }
        if (!phoneNumber.isEmpty()){
            gd.getLoggedInUser().setPhoneNumber(phoneNumber);
        }
        if (!address.isEmpty()){
            gd.getLoggedInUser().setAddress(address);
        }

        Toast.makeText(getContext(), "Account details successfully updated", Toast.LENGTH_SHORT).show();
        getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.VISIBLE);
        getActivity().getOnBackPressedDispatcher().onBackPressed();
    }

}