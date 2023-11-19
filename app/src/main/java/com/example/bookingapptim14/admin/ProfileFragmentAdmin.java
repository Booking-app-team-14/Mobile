package com.example.bookingapptim14.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.LoginScreen;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.UpdateAccountFragment;
import com.example.bookingapptim14.UpdateAccountPasswordFragment;

public class ProfileFragmentAdmin extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_admin, container, false);

        Button buttonCloseAccount = (Button) view.findViewById(R.id.buttonCloseAccount);
        buttonCloseAccount.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { cancelAccount(); } });

        Button buttonUpdateAccount = (Button) view.findViewById(R.id.buttonUpdateAccountDetails);
        buttonUpdateAccount.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { updateAccount(); } });

        TextView changePasswordTextView = (TextView) view.findViewById(R.id.changePasswordTextView);
        changePasswordTextView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { changePassword(); } });

        return view;
    }

    private void updateAccount() {
        Fragment fragment = new UpdateAccountFragment(); // (loggedInUser);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    private void cancelAccount() {
        new AlertDialog.Builder(getContext())
                .setTitle("Account closure confirmation")
                .setMessage("Are you sure you want to close your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        // TODO: Delete account from database

                        // if cant delete account, show error message and return

                        Toast.makeText(getContext(), "Account closed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), LoginScreen.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    private void changePassword() {
        Fragment fragment = new UpdateAccountPasswordFragment(); // (loggedInUser);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

}