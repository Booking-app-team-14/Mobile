package com.example.bookingapptim14;

import com.example.bookingapptim14.enums.AccommodationType;
import com.example.bookingapptim14.enums.NotificationType;
import com.example.bookingapptim14.enums.RequestStatus;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.Location;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.example.bookingapptim14.models.User;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsDTO;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ReviewStatus;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.GuestNotificationsData;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.ReservationRequestDTO;
import com.example.bookingapptim14.models.dtos.NotificationDTO.NotificationDTO;
import com.example.bookingapptim14.models.dtos.OwnersAccommodationDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.AccommodationReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewDTO;
import com.example.bookingapptim14.models.dtos.ReportsDTO.OwnerReviewReportsData;
import com.example.bookingapptim14.models.dtos.ReportsDTO.ReportStatus;
import com.example.bookingapptim14.models.dtos.ReportsDTO.UserReportsData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationData;
import com.example.bookingapptim14.models.dtos.ReservationRequestDTO.ApprovedReservationGuestData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GlobalData {

    private static GlobalData instance;
    private List<AccommodationRequest> accommodationRequest = new ArrayList<>();
    private User loggedInUser = new User();
    private List<SearchAccommodation> searchAccommodations = new ArrayList<>();
    private List<OwnersAccommodationDTO> ownersAccommodations = new ArrayList<>();
    private List<ApprovedReservationData> approvedReservations = new ArrayList<>();
    private List<ApprovedReservationGuestData> approvedGuestReservations = new ArrayList<>();
    private List<ApproveAccommodationReviewsData> accommodationCommentsAndReviews = new ArrayList<>();
    private List<ApproveOwnerReviewsData> ownerCommentsAndReviews = new ArrayList<>();
    private List<UserReportsData> userReports = new ArrayList<>();
    private List<AccommodationReviewReportsData> accommodationReviewReports = new ArrayList<>();
    private List<OwnerReviewReportsData> ownerReviewReports = new ArrayList<>();
    private List<GuestNotificationsData> guestNotifications = new ArrayList<>();

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

        ownersAccommodations.add(new OwnersAccommodationDTO(1L, "Sunny Beach House", "Hotel", 3, 4, "Katunska 4", 180, "jpg", ""));
        ownersAccommodations.add(new OwnersAccommodationDTO(2L, "Mountain View Cabin", "Apartment", 4, 7, "Hilandarska 2", 150.0, "jpg", ""));
        ownersAccommodations.add(new OwnersAccommodationDTO(3L, "Lakeside Villa", "Villa", 5, 10, "Mirijevska 51a", 250.0, "jpg", ""));
        ownersAccommodations.add(new OwnersAccommodationDTO(4L, "Downtown Loft", "Apartment", 4, 6, "Katunska 4", 220.0, "jpg", ""));
        ownersAccommodations.add(new OwnersAccommodationDTO(5L, "Rustic Country House", "Room", 5, 8, "Time Square", 210.0, "jpg", ""));
        ownersAccommodations.add(new OwnersAccommodationDTO(6L, "Seaside Bungalow", "Studio", 4, 10, "Katunska 4", 160.0, "jpg", ""));

        approvedReservations.add(new ApprovedReservationData(1L, 1L, 1L, LocalDate.now(), LocalDate.now(), 4, 540.0, "SENT", "Sunny Beach House", "Apartment", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest1@guest.com", "2"));
        approvedReservations.add(new ApprovedReservationData(2L, 2L, 2L, LocalDate.now(), LocalDate.now(), 7, 1050.0, "SENT", "Mountain View Cabin", "Studio", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest1@guest.com", "2"));
        approvedReservations.add(new ApprovedReservationData(3L, 3L, 3L, LocalDate.now(), LocalDate.now(), 10, 1750.0, "SENT", "Lakeside Villa", "Villa", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest2@guest.com", "2"));
        approvedReservations.add(new ApprovedReservationData(4L, 4L, 4L, LocalDate.now(), LocalDate.now(), 6, 1320.0, "SENT", "Downtown Loft", "Apartment", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest2@guest.com", "2"));

        approvedGuestReservations.add(new ApprovedReservationGuestData(1L, 1L, 1L, LocalDate.now().plusDays(4), LocalDate.now().plusDays(6), 4, 540.0, "SENT", "Sunny Beach House", "Apartment", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest1@guest.com", "2", "2"));
        approvedGuestReservations.add(new ApprovedReservationGuestData(2L, 2L, 2L, LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), 7, 1050.0, "SENT", "Mountain View Cabin", "Studio", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest1@guest.com", "2", "2"));
        approvedGuestReservations.add(new ApprovedReservationGuestData(3L, 3L, 3L, LocalDate.now().plusDays(3), LocalDate.now().plusDays(4), 10, 1750.0, "SENT", "Lakeside Villa", "Villa", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest2@guest.com", "2", "2"));
        approvedGuestReservations.add(new ApprovedReservationGuestData(4L, 4L, 4L, LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 6, 1320.0, "SENT", "Downtown Loft", "Apartment", "owner1@owner.com", "1350574775", 4, "", "png", "", "png", "11.11.2024", "guest2@guest.com", "2", "2"));

        accommodationCommentsAndReviews.add(new ApproveAccommodationReviewsData(1L, 1L, 1L, 4, "Great place to stay!", ReviewStatus.PENDING, LocalDateTime.now(), "guest1@guest.com", "jpg", "", "Sunny Beach House", "Apartment", "", "4"));
        accommodationCommentsAndReviews.add(new ApproveAccommodationReviewsData(2L, 2L, 2L, 5, "Amazing view!", ReviewStatus.PENDING, LocalDateTime.now(), "guest2@guest.com", "jpg", "", "Mountain View Cabin", "Studio", "", "5"));
        accommodationCommentsAndReviews.add(new ApproveAccommodationReviewsData(3L, 3L, 3L, 4, "Great place to stay!", ReviewStatus.PENDING, LocalDateTime.now(), "guest1@guest.com", "jpg", "", "Lakeside Villa", "Villa", "", "4"));
        accommodationCommentsAndReviews.add(new ApproveAccommodationReviewsData(4L, 4L, 4L, 5, "Amazing view!", ReviewStatus.PENDING, LocalDateTime.now(), "guest2@guest.com", "jpg", "", "Downtown Loft", "Apartment", "", "5"));

        ownerCommentsAndReviews.add(new ApproveOwnerReviewsData(1L, "Great place to stay!", 4, LocalDateTime.now(), false, false, 1L, 1L, "guest1@guest.com", "jpg", "", "owner1@owner.com", "jpg", "", "4"));
        ownerCommentsAndReviews.add(new ApproveOwnerReviewsData(2L, "Amazing view!", 5, LocalDateTime.now(), false, false, 2L, 2L, "guest1@guest.com", "jpg", "", "owner1@owner.com", "jpg", "", "5"));
        ownerCommentsAndReviews.add(new ApproveOwnerReviewsData(3L, "Great place to stay!", 4, LocalDateTime.now(), false, false, 3L, 3L, "guest2@guest.com", "jpg", "", "owner2@owner.com", "jpg", "", "4"));

        userReports.add(new UserReportsData(1L, 1L, 2L, 1, "Inappropriate behavior", LocalDateTime.now(), "guest1@guest.com", "jpg", "", "owner1@owner.com", "jpg", ""));
        userReports.add(new UserReportsData(2L, 2L, 3L, 1, "Inappropriate behavior", LocalDateTime.now(), "owner1@owner.com", "jpg", "", "guest1@guest.com", "jpg", ""));
        userReports.add(new UserReportsData(3L, 3L, 4L, 1, "Inappropriate behavior", LocalDateTime.now(), "guest2@guest.com", "jpg", "", "owner1@owner.com", "jpg", ""));

        AccommodationReviewDTO accommodationReview1 = new AccommodationReviewDTO(1L, 1L, 1L, 4, "Great place to stay!", ReviewStatus.PENDING, LocalDateTime.now());
        AccommodationReviewDTO accommodationReview2 = new AccommodationReviewDTO(2L, 2L, 2L, 5, "Amazing view!", ReviewStatus.PENDING, LocalDateTime.now());
        AccommodationReviewDTO accommodationReview3 = new AccommodationReviewDTO(3L, 3L, 3L, 4, "Great place to stay!", ReviewStatus.PENDING, LocalDateTime.now());

        accommodationReviewReports.add(new AccommodationReviewReportsData(1L, 1L, "Inappropriate behavior", ReportStatus.PENDING, LocalDateTime.now(), accommodationReview1, "", "guest1@guest.com", "Sunny Beach House", "Apartment", ""));
        accommodationReviewReports.add(new AccommodationReviewReportsData(2L, 2L, "Inappropriate behavior", ReportStatus.PENDING, LocalDateTime.now(), accommodationReview2, "", "guest1@guest.com", "Mountain View Cabin", "Studio", ""));
        accommodationReviewReports.add(new AccommodationReviewReportsData(3L, 3L, "Inappropriate behavior", ReportStatus.PENDING, LocalDateTime.now(), accommodationReview3, "", "guest1@guuest.com", "Lakeside Villa", "Villa", ""));

        OwnerReviewDTO ownerReview1 = new OwnerReviewDTO(1L,"bad person", 1, LocalDateTime.now(), true, true, 1L, 1L);
        OwnerReviewDTO ownerReview2 = new OwnerReviewDTO(2L,"awful personality", 5, LocalDateTime.now(), true, true, 2L, 2L);
        OwnerReviewDTO ownerReview3 = new OwnerReviewDTO(3L,"he is very unprofessional", 1, LocalDateTime.now(), true, true, 3L, 3L);

        ownerReviewReports.add(new OwnerReviewReportsData(1L, 1L, "Inappropriate behavior", ReportStatus.PENDING, LocalDateTime.now(), ownerReview1, "guest1@guest.com", "", "owner1@owner.com", "", "4.2"));
        ownerReviewReports.add(new OwnerReviewReportsData(2L, 2L, "Inappropriate behavior", ReportStatus.PENDING, LocalDateTime.now(), ownerReview2, "guest2@guest.com", "", "owner1@owner.com", "", "4.5"));
        ownerReviewReports.add(new OwnerReviewReportsData(3L, 3L, "Inappropriate behavior", ReportStatus.PENDING, LocalDateTime.now(), ownerReview3, "guest3@guest.com", "", "owner2@owner.com", "", "4"));

//        NotificationDTO notificationDTO1 = new NotificationDTO(1L, 1L, 2L, "2022-03-01T10:30:00", false, NotificationType.RESERVATION_REQUEST_RESPONSE);
        NotificationDTO notificationDTO = new NotificationDTO(1L, 1L, 2L, "1716558867", false, NotificationType.RESERVATION_REQUEST_RESPONSE);
        ReservationRequestDTO request1 = new ReservationRequestDTO(1L, 1L, 2L, LocalDate.now(), LocalDate.now().plusDays(2), 4, 540.0, RequestStatus.SENT, "Sunny Beach House", "APARTMENT", "jpg", "", "owner1@owner.com", "1716558867", 4, "png", "");
        ReservationRequestDTO request2 = new ReservationRequestDTO(2L, 2L, 3L, LocalDate.now(), LocalDate.now().plusDays(3), 7, 1050.0, RequestStatus.SENT, "Mountain View Cabin", "STUDIO", "jpg", "", "owner2@owner.com", "1716558867", 5, "png", "");
        ReservationRequestDTO request3 = new ReservationRequestDTO(3L, 3L, 4L, LocalDate.now(), LocalDate.now().plusDays(4), 10, 1750.0, RequestStatus.SENT, "Lakeside Villa", "VILLA", "jpg", "", "owner1@owner.com", "1716558867", 4, "png", "");

        guestNotifications.add(new GuestNotificationsData(notificationDTO, request1, true));
        guestNotifications.add(new GuestNotificationsData(notificationDTO, request2, false));
        guestNotifications.add(new GuestNotificationsData(notificationDTO, request3, true));
    }


    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public List<GuestNotificationsData> getGuestNotifications() {
        return guestNotifications;
    }
    public List<OwnerReviewReportsData> getOwnerReviewReports() {
        return ownerReviewReports;
    }
    public List<AccommodationReviewReportsData> getAccommodationReviewReports() {
        return accommodationReviewReports;
    }
    public List<UserReportsData> getUserReports() {
        return userReports;
    }
    public List<ApproveOwnerReviewsData> getOwnerCommentsAndReviews() {
        return ownerCommentsAndReviews;
    }
    public List<ApproveAccommodationReviewsData> getAccommodationCommentsAndReviews() {
        return accommodationCommentsAndReviews;
    }
    public List<ApprovedReservationGuestData> getApprovedGuestReservations() {
        return approvedGuestReservations;
    }
    public List<ApprovedReservationData> getApprovedReservations() { return approvedReservations; }
    public List<OwnersAccommodationDTO> getOwnersAccommodations() {
        return ownersAccommodations;
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