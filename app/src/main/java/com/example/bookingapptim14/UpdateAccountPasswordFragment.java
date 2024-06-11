package com.example.bookingapptim14;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateAccountPasswordFragment extends Fragment {

    public UpdateAccountPasswordFragment() { }

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
        TextView passwordTextView = getActivity().findViewById(R.id.editTextNewPassword);
        String newPassword = passwordTextView.getText().toString();
        TextView confirmPasswordTextView = getActivity().findViewById(R.id.editTextConfirmNewPassword);
        String confirmPassword = confirmPasswordTextView.getText().toString();
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword.length() < 8) {
            Toast.makeText(getContext(), "Password needs to be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);
        String jwtToken = sharedPreferences.getString("jwtToken", "");

        // PUT /users/{id}/password, consumes String
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/password");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    try(DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                        os.writeBytes(newPassword);
                        os.flush();
                    }

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
                                getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.VISIBLE);
                                getActivity().getOnBackPressedDispatcher().onBackPressed();
                            }
                        });
                    } else {
                        System.out.println("PUT request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}