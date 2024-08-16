package com.example.bookingapptim14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingapptim14.guest.MainActivityGuest;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class RegisterScreen extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText addressEditText;
    private EditText phoneNumberEditText;
    private RadioGroup roleRadioGroup;
    private Button registerButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        // inicijalizacija elemenata sa layouta
        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        firstNameEditText = findViewById(R.id.editTextName);
        lastNameEditText = findViewById(R.id.editTextSurname);
        addressEditText = findViewById(R.id.editTextAddress);
        phoneNumberEditText = findViewById(R.id.editTextNumber);
        roleRadioGroup = findViewById(R.id.radioGroup);
        registerButton = findViewById(R.id.buttonRegister);
        loginLink = findViewById(R.id.textViewLogin);

        ImageView imageView = findViewById(R.id.imageViewLogo);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();




        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_3);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        Drawable passwordIcon2 = getResources().getDrawable(R.drawable.img_3);
        passwordIcon2.setBounds(15, 1, (int) (passwordIcon2.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        confirmPasswordEditText.setCompoundDrawables(null, null, passwordIcon2, null);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();
                String role = selectedRoleId == R.id.radioButtonGuest ? "GUEST" : "OWNER";

                if (validateInputs(username, password, confirmPassword, firstName, lastName, address, phoneNumber)) {
                    registerUser(username, password, confirmPassword, firstName, lastName, address, phoneNumber, role);
                } else {
                    Toast.makeText(RegisterScreen.this, "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(String username, String password, String confirmPassword, String firstName, String lastName, String address, String phoneNumber) {
        // Validacija svih unosa, na primer:
        return !username.isEmpty() && !password.isEmpty() && password.equals(confirmPassword) && !firstName.isEmpty() && !lastName.isEmpty() && !address.isEmpty() && !phoneNumber.isEmpty();
    }

    private void registerUser(final String username, final String password, final String confirmPassword, final String firstName, final String lastName, final String address, final String phoneNumber, final String role) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/register/users?type=" + role);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json; utf-8");

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("username", username);
                    jsonParam.put("password", password);
                    jsonParam.put("firstName", firstName);
                    jsonParam.put("lastName", lastName);
                    jsonParam.put("address", address);
                    jsonParam.put("phoneNumber", phoneNumber);
                    jsonParam.put("role", role);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_CREATED) {
                        // Ako je registracija uspešna
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showSuccessMessage();
                                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {
                        // Ako je registracija neuspešna
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showUnsuccessMessage();
                            }
                        });
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterScreen.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void showSuccessMessage() {
        Toast.makeText(RegisterScreen.this, "You have successfully created your account!", Toast.LENGTH_SHORT).show();
    }

    private void showUnsuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration Failed")
                .setMessage("Please try again later.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}