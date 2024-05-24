package com.example.bookingapptim14.guest;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.LoginScreen;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.UpdateAccountFragment;
import com.example.bookingapptim14.UpdateAccountPasswordFragment;
import com.example.bookingapptim14.host.MyAccommodationsFragment;
import com.example.bookingapptim14.models.User;
import com.example.bookingapptim14.models.dtos.UserBasicInfoDTO;

import org.w3c.dom.Text;

import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragmentGuest extends Fragment {

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
                // TODO
//                Fragment fragment = new
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
            }
        });

        // GET api/users/token/{jwtToken} -> userId (long)
        // GET api/users/{id}/basicInfo -> UserBasicInfoDTO
        // TODO: Get logged in user from database
//        GlobalData gd = GlobalData.getInstance();
//        User loggedUser = gd.getLoggedInUser();
        UserBasicInfoDTO user = new UserBasicInfoDTO("John", "Doe", "guest.john@gmail.com", "123 Main St, NY, USA", "+381000000000","");
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
        //

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
                        // TODO: Delete account from database
                        // DELETE api/users/{userId}
                        // userId should be in onCreateView method (probably extract it to a field)

                        // if cant delete account, show error message and return
                        // Toast.makeText(getContext(), "You have active reservations! Account cannot be closed.", Toast.LENGTH_SHORT).show();
                        // return;

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