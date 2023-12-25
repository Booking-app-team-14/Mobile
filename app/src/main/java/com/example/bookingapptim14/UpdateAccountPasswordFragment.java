package com.example.bookingapptim14;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UpdateAccountPasswordFragment extends Fragment {

    // private User loggedInUser;

    public UpdateAccountPasswordFragment() { //User loggedInUser) {
        // TODO: Pass user to fragment
        // this.loggedInUser = loggedInUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_account_password, container, false);

        Button buttonChangePassword = (Button) view.findViewById(R.id.buttonChangePassword);
        buttonChangePassword.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { changePassword(); } });

        ImageButton buttonBack = (ImageButton) view.findViewById(R.id.backButton);
        buttonBack.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {
            getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.VISIBLE);
            getActivity().getOnBackPressedDispatcher().onBackPressed();
        }});

        getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.GONE);

        return view;
    }

    private void changePassword() {
        // TODO: Change password
        TextView passwordTextView = getActivity().findViewById(R.id.editTextNewPassword);
        String newPassword = passwordTextView.getText().toString();
        TextView confirmPasswordTextView = getActivity().findViewById(R.id.editTextConfirmNewPassword);
        String confirmPassword = confirmPasswordTextView.getText().toString();
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        GlobalData gd = GlobalData.getInstance();
        User loggedUser = gd.getLoggedInUser();
        loggedUser.setPassword(newPassword);
        Toast.makeText(getContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
        getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.VISIBLE);
        getActivity().getOnBackPressedDispatcher().onBackPressed();
    }

}