package com.example.bookingapptim14.admin.reports;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookingapptim14.Adapters.AccommodationApprovalAdapter;
import com.example.bookingapptim14.Adapters.AdminUserReportsAdapter;
import com.example.bookingapptim14.BuildConfig;
import com.example.bookingapptim14.GlobalData;
import com.example.bookingapptim14.R;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserReportsAdminFragment extends Fragment implements AdminUserReportsAdapter.OnReportListener {

    private RecyclerView userReportsRecyclerView;
    private AdminUserReportsAdapter adapter;
    private String jwtToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_reports_admin, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("jwtToken", "");

        userReportsRecyclerView = view.findViewById(R.id.adminUserReportsRecyclerView);
        adapter = new AdminUserReportsAdapter(new ArrayList<>(), this);
        userReportsRecyclerView.setAdapter(adapter);
        userReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchUserReports();

        return view;
    }

    private void fetchUserReports() {
        // GET api/userReports -> UserReportsDTO
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/userReports");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        List<UserReportsData> userReports = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<UserReportsDTO>>(){}.getType();
                        List<UserReportsDTO> userReportDTOs = gson.fromJson(response.toString(), listType);

                        for (UserReportsDTO dto : userReportDTOs) {
                            // GET users/{reportingUserId}/image-type-username -> reportingUserUsername + " | " + reportingUserProfilePictureType + " | " + reportingUserProfilePictureBytes
                            URL reportingUserUrl = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getReportingUserId() + "/image-type-username");
                            HttpURLConnection reportingUserConn = (HttpURLConnection) reportingUserUrl.openConnection();
                            reportingUserConn.setRequestMethod("GET");
                            reportingUserConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int reportingUserResponseCode = reportingUserConn.getResponseCode();

                            String reportingUserUsername = "";
                            String reportingUserProfilePictureType = "";
                            String reportingUserProfilePictureBytes = "";

                            if (reportingUserResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader reportingUserIn = new BufferedReader(new InputStreamReader(reportingUserConn.getInputStream()));
                                String reportingUserInputLine;
                                StringBuffer reportingUserResponse = new StringBuffer();
                                while ((reportingUserInputLine = reportingUserIn.readLine()) != null) {
                                    reportingUserResponse.append(reportingUserInputLine);
                                }
                                reportingUserIn.close();

                                String[] reportingUserResponseParts = reportingUserResponse.toString().split(" \\| ");
                                reportingUserUsername = reportingUserResponseParts[0];
                                reportingUserProfilePictureType = reportingUserResponseParts[1];
                                reportingUserProfilePictureBytes = reportingUserResponseParts[2];
                            }

                            // GET users/{reportedUserId}/image-type-username -> reportedUserUsername + " | " + reportedUserProfilePictureType + " | " + reportedUserProfilePictureBytes
                            URL reportedUserUrl = new URL(BuildConfig.IP_ADDR + "/api/users/" + dto.getReportedUserId() + "/image-type-username");
                            HttpURLConnection reportedUserConn = (HttpURLConnection) reportedUserUrl.openConnection();
                            reportedUserConn.setRequestMethod("GET");
                            reportedUserConn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                            int reportedUserResponseCode = reportedUserConn.getResponseCode();

                            String reportedUserUsername = "";
                            String reportedUserProfilePictureType = "";
                            String reportedUserProfilePictureBytes = "";

                            if (reportedUserResponseCode == HttpURLConnection.HTTP_OK) {
                                BufferedReader reportedUserIn = new BufferedReader(new InputStreamReader(reportedUserConn.getInputStream()));
                                String reportedUserInputLine;
                                StringBuffer reportedUserResponse = new StringBuffer();
                                while ((reportedUserInputLine = reportedUserIn.readLine()) != null) {
                                    reportedUserResponse.append(reportedUserInputLine);
                                }
                                reportedUserIn.close();

                                String[] reportedUserResponseParts = reportedUserResponse.toString().split(" \\| ");
                                reportedUserUsername = reportedUserResponseParts[0];
                                reportedUserProfilePictureType = reportedUserResponseParts[1];
                                reportedUserProfilePictureBytes = reportedUserResponseParts[2];
                            }

                            UserReportsData userReport = new UserReportsData(dto, reportingUserUsername, reportingUserProfilePictureType, reportingUserProfilePictureBytes, reportedUserUsername, reportedUserProfilePictureType, reportedUserProfilePictureBytes);
                            userReports.add(userReport);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setUserReports(userReports);
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
    }

    @Override
    public void onUserBlocked(UserReportsData userReport) {
        // PUT /api/userReports/block-user/{reportId}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/userReports/block-user/" + userReport.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.removeItem(userReport);
                                Toast.makeText(getContext(), "User successfully blocked!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onReportDismissed(UserReportsData userReport) {
        // DELETE /api/userReports/{id}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(BuildConfig.IP_ADDR + "/api/userReports/" + userReport.getId());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.setRequestProperty("Authorization", "Bearer " + jwtToken);

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.removeItem(userReport);
                                Toast.makeText(getContext(), "Report dismissed!", Toast.LENGTH_SHORT).show();
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

}