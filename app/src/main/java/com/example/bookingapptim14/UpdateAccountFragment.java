package com.example.bookingapptim14;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bookingapptim14.models.dtos.UserBasicInfoDTO;
import com.example.bookingapptim14.models.dtos.UserBasicInfoNoImageDTO;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateAccountFragment extends Fragment {

    private UserBasicInfoDTO userInfo;
    private Bitmap selectedImageBitmap;
    private Long userId;
    private String jwtToken;

    public UpdateAccountFragment() { }

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
                        userInfo.setFirstName(jsonObject.getString("firstName"));
                        userInfo.setLastName(jsonObject.getString("lastName"));
                        userInfo.setEmail(jsonObject.getString("email"));
                        userInfo.setAddress(jsonObject.getString("address"));
                        userInfo.setPhoneNumber(jsonObject.getString("phoneNumber"));
                        userInfo.setProfilePictureBytes(jsonObject.getString("profilePictureBytes"));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageView imageView = view.findViewById(R.id.newProfilePictureImageView);
                                if (!userInfo.getProfilePictureBytes().isEmpty()) {
                                    byte[] decodedString = java.util.Base64.getDecoder().decode(userInfo.getProfilePictureBytes());
                                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
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

        Button loadPictureButton = view.findViewById(R.id.newProfilePictureImageViewButtonLoadPicture);

        loadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            try {
                selectedImageBitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                ImageView imageView = getActivity().getWindow().findViewById(R.id.newProfilePictureImageView);
                imageView.setImageBitmap(selectedImageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String getBase64StringFromBitmap(Bitmap bitmap) {
        if (bitmap == null || (bitmap.getWidth() == 0 && bitmap.getHeight() == 0)) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void updateDetails() {
        String firstName = ((EditText) getActivity().getWindow().findViewById(R.id.editTextName)).getText().toString();
        String lastName = ((EditText) getActivity().getWindow().findViewById(R.id.editTextSurname)).getText().toString();
        String address = ((EditText) getActivity().getWindow().findViewById(R.id.editTextAddress)).getText().toString();
        String phoneNumber = ((EditText) getActivity().getWindow().findViewById(R.id.editTextPhone)).getText().toString();

        if (firstName.length() < 2){
            Toast.makeText(getContext(), "First name must be at least 2 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        if (lastName.length() < 2){
            Toast.makeText(getContext(), "Last name must be at least 2 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        if (address.length() < 5){
            Toast.makeText(getContext(), "Address must be at least 5 characters long", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isPhoneNumberValid(phoneNumber)) {
            Toast.makeText(getContext(), "Invalid phone number. Example: +381012345678", Toast.LENGTH_SHORT).show();
            return;
        }

        String newProfilePictureBytesString = getBase64StringFromBitmap(selectedImageBitmap);
        if (newProfilePictureBytesString != null && !newProfilePictureBytesString.isEmpty()) {
            // POST users/{userId}/image, consumes String
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/image");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                        conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                        try(DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                            os.writeBytes(newProfilePictureBytesString);
                            os.flush();
                        }

                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_CREATED) {
                            System.out.println("Profile picture updated successfully!");
                        } else {
                            System.out.println("POST request failed!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        UserBasicInfoNoImageDTO user = new UserBasicInfoNoImageDTO();
        if (!firstName.isEmpty()){
            user.setFirstName(firstName);
        } else{
            user.setFirstName(userInfo.getFirstName());
        }
        if (!lastName.isEmpty()){
            user.setLastName(lastName);
        } else {
            user.setLastName(userInfo.getLastName());
        }
        if (!address.isEmpty()){
            user.setAddress(address);
        } else {
            user.setAddress(userInfo.getAddress());
        }
        if (!phoneNumber.isEmpty()){
            user.setPhoneNumber(phoneNumber);
        } else {
            user.setPhoneNumber(userInfo.getPhoneNumber());
        }

        // PUT /users/{id}/basicInfo, consumes UserBasicInfoNoImageDTO
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/users/" + userId + "/basicInfo");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("firstName", user.getFirstName());
                    jsonObject.put("lastName", user.getLastName());
                    jsonObject.put("address", user.getAddress());
                    jsonObject.put("phoneNumber", user.getPhoneNumber());

                    try(DataOutputStream os = new DataOutputStream(conn.getOutputStream())) {
                        os.writeBytes(jsonObject.toString());
                        os.flush();
                    }

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "Account details successfully updated", Toast.LENGTH_SHORT).show();
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

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\+\\d{1,2}\\s?\\d{3}\\s?\\d{3}\\s?\\d{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


}