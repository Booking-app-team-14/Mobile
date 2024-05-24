package com.example.bookingapptim14;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateAccountFragment extends Fragment {

    private UserBasicInfoDTO userInfo;
    private Bitmap selectedImageBitmap;
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

        // GET api/users/{id}/basicInfo -> UserBasicInfoDTO
        // TODO: Get logged in user from database
        userInfo = new UserBasicInfoDTO("John", "Doe", "admin.john@gmail.com", "123 Main St, NY, USA", "+381000000000","");

        ImageView imageView = view.findViewById(R.id.newProfilePictureImageView);
        if (!userInfo.getProfilePictureBytes().isEmpty()) {
            byte[] decodedString = java.util.Base64.getDecoder().decode(userInfo.getProfilePictureBytes());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        }

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

        // TODO: Update profile picture
        // POST users/{userId}/image, consumes String
        String newProfilePictureBytesString = getBase64StringFromBitmap(selectedImageBitmap);
        if (newProfilePictureBytesString != null && !newProfilePictureBytesString.isEmpty()) {
//            newProfilePictureBytesString
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

        // TODO: Update account details
        // send PUT request
        // PUT /users/{id}/basicInfo, consumes UserBasicInfoNoImageDTO

        Toast.makeText(getContext(), "Account details successfully updated", Toast.LENGTH_SHORT).show();
        getActivity().getWindow().findViewById(R.id.bottomNavView).setVisibility(View.VISIBLE);
        getActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\+\\d{1,2}\\s?\\d{3}\\s?\\d{3}\\s?\\d{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


}