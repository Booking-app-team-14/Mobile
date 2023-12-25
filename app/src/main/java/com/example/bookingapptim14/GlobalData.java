package com.example.bookingapptim14;

import com.example.bookingapptim14.models.AccommodationRequest;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    private static GlobalData instance;
    private List<AccommodationRequest> accommodationRequest = new ArrayList<>();

    private GlobalData() {
        accommodationRequest.add(new AccommodationRequest(1L, "Accommodation 1", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ...", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(2L, "Accommodation 2", "Studio", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ...", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(3L, "Accommodation 3", "Apartment", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ...", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(4L, "Accommodation 4", "Studio", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ...", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(5L, "Accommodation 5", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ...", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(6L, "Accommodation 6", "Studio", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ...", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(7L, "Accommodation 7", "Apartment", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ...", 4, "jpg", ""));
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public List<AccommodationRequest> getAccommodationRequest() {
        return accommodationRequest;
    }

    public void addAccommodation(AccommodationRequest request) {
        // TODO: Add accommodation
    }

}