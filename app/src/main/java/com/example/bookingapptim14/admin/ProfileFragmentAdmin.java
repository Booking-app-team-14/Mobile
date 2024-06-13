package com.example.bookingapptim14.admin;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.LoginScreen;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.UpdateAccountFragment;
import com.example.bookingapptim14.UpdateAccountPasswordFragment;
import com.example.bookingapptim14.models.User;
import com.example.bookingapptim14.models.dtos.UserBasicInfoDTO;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragmentAdmin extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_admin, container, false);

        Button buttonUpdateAccount = (Button) view.findViewById(R.id.buttonUpdateAccountDetails);
        buttonUpdateAccount.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { updateAccount(); } });

        TextView changePasswordTextView = (TextView) view.findViewById(R.id.changePasswordTextView);
        changePasswordTextView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { changePassword(); } });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);
        String jwtToken = sharedPreferences.getString("jwtToken", "");

        // GET api/users/{id}/basicInfo -> UserBasicInfoDTO
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/basicInfo");
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

                        // Parse the JSON response into a UserBasicInfoDTO object
                        JSONObject jsonObject = new JSONObject(content.toString());
                        UserBasicInfoDTO user = new UserBasicInfoDTO();
                        user.setFirstName(jsonObject.getString("firstName"));
                        user.setLastName(jsonObject.getString("lastName"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setAddress(jsonObject.getString("address"));
                        user.setPhoneNumber(jsonObject.getString("phoneNumber"));
                        user.setProfilePictureBytes(jsonObject.getString("profilePictureBytes"));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView emailTextView = view.findViewById(R.id.adminEmailTextView);
                                emailTextView.setText(user.getEmail());
                                TextView firstNameTextView = view.findViewById(R.id.adminNameSurnameTextView);
                                firstNameTextView.setText(user.getFirstName() + " " + user.getLastName());
                                TextView phoneNumberTextView = view.findViewById(R.id.adminPhoneNumberTextView);
                                phoneNumberTextView.setText(user.getPhoneNumber());
                                TextView addressTextView = view.findViewById(R.id.adminAddressTextView);
                                addressTextView.setText(user.getAddress());
                                CircleImageView profilePicture = view.findViewById(R.id.adminProfilePictureImage);
                                String base64Image = user.getProfilePictureBytes();
                                if (base64Image != null && !base64Image.isEmpty()) {
                                    byte[] decodedString = Base64.getDecoder().decode(base64Image);
                                    profilePicture.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                                }
                            }
                        });
                    } else {
                        System.out.println("GET request failed!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }

    private void updateAccount() {
        Fragment fragment = new UpdateAccountFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    private void changePassword() {
        Fragment fragment = new UpdateAccountPasswordFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

}