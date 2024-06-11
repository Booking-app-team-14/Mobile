package com.example.bookingapptim14.guest;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.LoginScreen;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.UpdateAccountFragment;
import com.example.bookingapptim14.UpdateAccountPasswordFragment;
import com.example.bookingapptim14.host.MyAccommodationsFragment;
import com.example.bookingapptim14.models.User;
import com.example.bookingapptim14.models.dtos.UserBasicInfoDTO;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragmentGuest extends Fragment {

    private Long userId;
    private String jwtToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_guest, container, false);

        Button buttonCloseAccount = (Button) view.findViewById(R.id.buttonCloseAccount);
        buttonCloseAccount.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { cancelAccount(); } });

        Button buttonUpdateAccount = (Button) view.findViewById(R.id.buttonUpdateAccountDetails);
        buttonUpdateAccount.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { updateAccount(); } });

        TextView changePasswordTextView = (TextView) view.findViewById(R.id.changePasswordTextView);
        changePasswordTextView.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { changePassword(); } });

        LinearLayout myReservations = (LinearLayout) view.findViewById(R.id.profileGuestReservations);
        myReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ApprovedReservationsFragmentGuest();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });

        LinearLayout myReservationRequests = (LinearLayout) view.findViewById(R.id.profileGuestRequests);
        myReservationRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: make my reservation requests fragment for guest
//                Fragment fragment = new
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);
        jwtToken = sharedPreferences.getString("jwtToken", "");

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
                                TextView emailTextView = view.findViewById(R.id.guestEmailTextView);
                                emailTextView.setText(user.getEmail());
                                TextView firstNameTextView = view.findViewById(R.id.guestNameSurnameTextView);
                                firstNameTextView.setText(user.getFirstName() + " " + user.getLastName());
                                TextView phoneNumberTextView = view.findViewById(R.id.guestPhoneNumberTextView);
                                phoneNumberTextView.setText(user.getPhoneNumber());
                                TextView addressTextView = view.findViewById(R.id.guestAddressTextView);
                                addressTextView.setText(user.getAddress());
                                CircleImageView profilePicture = view.findViewById(R.id.guestProfilePictureImage);
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

    private void cancelAccount() {
        new AlertDialog.Builder(getContext())
                .setTitle("Account closure confirmation")
                .setMessage("Are you sure you want to close your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // DELETE api/users/{userId}
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("DELETE");
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

                                        if (!content.toString().equals("Account Deleted")) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getContext(), "You have active reservations! Account cannot be closed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            return;
                                        }
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Clear shared preferences
                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.clear();
                                                editor.apply();

                                                Toast.makeText(getContext(), "Account closed", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity(), LoginScreen.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }
                                        });
                                    } else {
                                        System.out.println("DELETE request failed!");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    private void changePassword() {
        Fragment fragment = new UpdateAccountPasswordFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

}