package com.example.bookingapptim14;

import com.example.bookingapptim14.enums.AccommodationType;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.Location;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.example.bookingapptim14.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GlobalData {

    private static GlobalData instance;
    private List<AccommodationRequest> accommodationRequest = new ArrayList<>();
    private User loggedInUser = new User();
    private List<SearchAccommodation> searchAccommodations = new ArrayList<>();

    private GlobalData() {
        accommodationRequest.add(new AccommodationRequest(13L, "Novi apartman 1", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 1", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(14L, "Novi apartman 2", "Studio", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ... opis 2", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(15L, "Novi apartman 3", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 3", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(16L, "Novi apartman 4", "Studio", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ... opis 4", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(17L, "Novi apartman 5", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 5", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(18L, "Novi apartman 6", "Studio", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 6", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(19L, "Novi apartman 7", "Apartment", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ... opis 7", 4, "jpg", ""));

        searchAccommodations.add(new SearchAccommodation(1L, "Sunny Beach House", "Enjoy the sun and sea at this delightful beach house.", AccommodationType.HOTEL, new Location(1L,"Serbia","Zlatibor","Katunska 4"), "accommodation_13", 2.6,2,4, 180.0, new HashSet<>(),true));
        searchAccommodations.add(new SearchAccommodation(2L, "Mountain View Cabin", "Breathtaking mountain views from this cozy cabin retreat.",AccommodationType.APARTMENT, new Location(2L,"Serbia","Novi Sad","Hilandarska 2"), "rectangle", 4.9,2 ,7,150.0,new HashSet<>(),true));
        searchAccommodations.add(new SearchAccommodation(3L, "Lakeside Villa", "Stunning villa by the lake with serene views.", AccommodationType.VILLA, new Location(3L,"Serbia","Belgrade","Mirijevska 51a"), "accommodation_2", 4.0, 3, 10,250.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(4L, "Downtown Loft", "Modern loft in the heart of the city.", AccommodationType.APARTMENT,new Location(4L,"Croatia","Zagreb","Katunska 4"), "accommodation_3", 3.3, 4,6, 220.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(5L, "Rustic Country House", "Escape to the countryside in this cozy house.", AccommodationType.ROOM,new Location(5L,"USA","New York","Time Square"), "accommodation_5", 4.7, 5,8, 210.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(6L, "Seaside Bungalow", "Relaxing bungalow near the beach.", AccommodationType.STUDIO,new Location(6L,"Bulgaria","Plovdiv","Katunska 4"), "accommodation_4", 4.5, 2,10, 160.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(7L, "Luxury Penthouse", "Experience luxury in this exquisite penthouse.", AccommodationType.APARTMENT,new Location(7L,"Serbia","Zlatibor","Katunska 2"), "accommodation_6", 5.0, 4,8, 350.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(8L, "Countryside Cottage", "Charming cottage in a peaceful rural setting.", AccommodationType.ROOM, new Location(8L,"Croatia","Zagreb","Katunska 66"),"accommodation_7", 3.6, 3,5, 180.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(9L, "Tropical Paradise Villa", "Tropical villa surrounded by lush greenery.", AccommodationType.VILLA,new Location(9l,"Serbia","Zlatibor","Katunska 56789"), "accommodation_8", 2.7, 5,7, 300.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(10L, "Beachfront Resort", "Relaxing resort right by the beach.", AccommodationType.STUDIO, new Location(10l,"Norway","Oslo","Katunska 4"), "accommodation_9", 4.8, 4, 12,280.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(11L, "Chic Urban Studio", "Stylish studio apartment in a trendy neighborhood.", AccommodationType.APARTMENT,new Location(11l,"Hungary","Budapest","Katunska 4"), "accommodation_10", 3.4, 1,3, 90.0,new HashSet<>(), true));
        searchAccommodations.add(new SearchAccommodation(12L, "Mountain Retreat Chalet", "Escape to this cozy chalet nestled in the mountains.", AccommodationType.VILLA,new Location(12l,"Serbia","Zlatibor","Katunska 543"), "accommodation_11", 2.6, 3,5, 210.0,new HashSet<>(), true));
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

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public List<SearchAccommodation> getSearchAccommodations() {
        return searchAccommodations;
    }

    public void addAccommodation(AccommodationRequest request) {
        // TODO: Add accommodation
        searchAccommodations.add(new SearchAccommodation(request.getAccommodationId(), request.getName(), "Lorem ipsum ...", AccommodationType.valueOf(request.getType().toUpperCase()), new Location(1L,"Serbia","Zlatibor","Katunska 4"), "accommodation_6", (double) request.getStars(),2,4, 180.0, new HashSet<>(),true));
    }

}