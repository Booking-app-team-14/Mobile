package com.example.bookingapptim14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import com.example.bookingapptim14.admin.MainActivityAdmin;
import com.example.bookingapptim14.guest.AccommodationDetailsActivityGuest;
import com.example.bookingapptim14.guest.MainActivityGuest;
import com.example.bookingapptim14.host.MainActivityHost;
import com.example.bookingapptim14.notifications.WebSocketManager;

import org.json.JSONObject;

public class LoginScreen extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // inicijalizacija elemenata sa layouta
        usernameEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        TextView signUpLink = findViewById(R.id.textViewSignUp);

        ImageView imageView = findViewById(R.id.imageView);
        ObjectAnimator rotationAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        rotationAnimator.setTarget(imageView);
        rotationAnimator.start();

        //podesavanje velicine ikonica
        //Drawable emailIcon = getResources().getDrawable(R.drawable.img_4);
        //emailIcon.setBounds(15, 1, (int) (emailIcon.getIntrinsicWidth() * 0.06), (int) (emailIcon.getIntrinsicHeight() * 0.06));
        //usernameEditText.setCompoundDrawables(emailIcon, null, null, null);

        Drawable passwordIcon = getResources().getDrawable(R.drawable.img_3);
        passwordIcon.setBounds(15, 1, (int) (passwordIcon.getIntrinsicWidth() * 0.05), (int) (passwordIcon.getIntrinsicHeight() * 0.05));
        passwordEditText.setCompoundDrawables(null, null, passwordIcon, null);

        //dugme za prijavu
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WebSocketManager.getInstance(getApplicationContext());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(BuildConfig.IP_ADDR + "/api/login");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);
                            conn.setRequestProperty("Content-Type", "application/json; utf-8");

                            JSONObject jsonParam = new JSONObject();
                            jsonParam.put("username", usernameEditText.getText().toString());
                            jsonParam.put("password", passwordEditText.getText().toString());

                            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                            os.writeBytes(jsonParam.toString());

                            os.flush();
                            os.close();

                            int responseCode = conn.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                String inputLine;
                                StringBuilder content = new StringBuilder();
                                while ((inputLine = in.readLine()) != null) {
                                    content.append(inputLine);
                                }

                                // close connections
                                in.close();
                                conn.disconnect();

                                String jwtToken = content.toString();
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                myEdit.putString("jwtToken", jwtToken);
                                myEdit.apply();

                                url = new URL(BuildConfig.IP_ADDR + "/api/users/token/" + jwtToken);
                                conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setDoInput(true);

                                responseCode = conn.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    String inputLineId;
                                    StringBuilder contentId = new StringBuilder();
                                    while ((inputLineId = in.readLine()) != null) {
                                        contentId.append(inputLineId);
                                    }
                                    in.close();
                                    conn.disconnect();
                                    Long userId = Long.parseLong(contentId.toString());

                                    myEdit.putLong("userId", userId);
                                    myEdit.apply();

                                    // ovako se cita userId iz sharedPreferences
                                    // SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                    // Long userId = sharedPreferences.getLong("userId", -1); // -1 is the default value if "userId" does not exist
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showUnsuccessMessage();
                                        }
                                    });
                                    return;
                                }

                                url = new URL(BuildConfig.IP_ADDR + "/api/role");
                                conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setDoInput(true);

                                // ovako se cita jwt token iz sharedPreferences
                                // SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                // String jwtToken = sharedPreferences.getString("jwtToken", "");
                                if (!jwtToken.isEmpty()) {
                                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);
                                }

                                responseCode = conn.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    content = new StringBuilder();
                                    while ((inputLine = in.readLine()) != null) {
                                        content.append(inputLine);
                                    }

                                    // close connections
                                    in.close();
                                    conn.disconnect();

                                    String role = content.toString();

                                    if (role.equals("GUEST")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(LoginScreen.this, MainActivityGuest.class);
                                                showSuccessMessage();
                                                Log.d("Here","passed");
                                                startActivity(intent);
                                                Log.d("Here","passed2");
                                                finish();
                                            }
                                        });
                                    } else if (role.equals("OWNER")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(LoginScreen.this, MainActivityHost.class);
                                                showSuccessMessage();
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    } else if (role.equals("ADMIN")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(LoginScreen.this, MainActivityAdmin.class);
                                                showSuccessMessage();
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showUnsuccessMessage();
                                            }
                                        });
                                    }
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showUnsuccessMessage();
                                        }
                                    });
                                }
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showUnsuccessMessage();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginScreen.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        //hyperlink za registraciju
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
    }


    public void onForgotPasswordClick(View view) {
        //dodati kod
    }

    private void showSuccessMessage() {
        Toast.makeText(LoginScreen.this, "You successfully logged in!", Toast.LENGTH_SHORT).show();
    }

    private void showUnsuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unsuccessful login\n")
                .setMessage("Bad credentials! Try again!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}