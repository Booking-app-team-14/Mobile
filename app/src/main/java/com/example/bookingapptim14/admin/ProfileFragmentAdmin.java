package com.example.bookingapptim14.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.LoginScreen;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.UpdateAccountFragment;
import com.example.bookingapptim14.UpdateAccountPasswordFragment;
import com.example.bookingapptim14.models.User;
import com.example.bookingapptim14.models.dtos.UserBasicInfoDTO;

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

        // GET api/users/token/{jwtToken} -> userId (long)
        // GET api/users/{id}/basicInfo -> UserBasicInfoDTO
        // TODO: Get logged in user from database
//        GlobalData gd = GlobalData.getInstance();
//        User loggedUser = gd.getLoggedInUser();
        UserBasicInfoDTO user = new UserBasicInfoDTO("John", "Doe", "admin.john@gmail.com", "123 Main St, NY, USA", "+381000000000","");
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
        //

        return view;
    }

    private void updateAccount() {
        Fragment fragment = new UpdateAccountFragment(); // (loggedInUser);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

    private void changePassword() {
        Fragment fragment = new UpdateAccountPasswordFragment(); // (loggedInUser);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
    }

}