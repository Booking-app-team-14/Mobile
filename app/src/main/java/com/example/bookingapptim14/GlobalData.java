package com.example.bookingapptim14;

import com.example.bookingapptim14.enums.AccommodationType;
import com.example.bookingapptim14.enums.NotificationType;
import com.example.bookingapptim14.enums.RequestStatus;
import com.example.bookingapptim14.models.AccommodationRequest;
import com.example.bookingapptim14.models.Amenity;
import com.example.bookingapptim14.models.Availability;
import com.example.bookingapptim14.models.Location;
import com.example.bookingapptim14.models.SearchAccommodation;
import com.example.bookingapptim14.models.User;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationReportDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AccommodationUpdateDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.AmenityDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.LocationDTO;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.MonthlyAccommodationReport;
import com.example.bookingapptim14.models.dtos.AccommodationDTO.UpdateAvailabilityDTO;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveAccommodationReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsDTO;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ApproveOwnerReviewsData;
import com.example.bookingapptim14.models.dtos.ApproveReviewsDTO.ReviewStatus;
import com.example.bookingapptim14.models.dtos.Image;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GlobalData {

    private static GlobalData instance;
    private List<AccommodationRequest> accommodationRequest = new ArrayList<>();
    private User loggedInUser = new User();
    private List<Availability> availabilities = new ArrayList<>();
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
    private AccommodationUpdateDTO accommodationUpdateDTO = new AccommodationUpdateDTO();

    private List<AccommodationReportDTO> accommodationReportDTOS = new ArrayList<AccommodationReportDTO>();

    private GlobalData() {
        accommodationRequest.add(new AccommodationRequest(13L, "Novi apartman 1", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 1", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(14L, "Novi apartman 2", "Studio", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ... opis 2", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(15L, "Novi apartman 3", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 3", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(16L, "Novi apartman 4", "Studio", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ... opis 4", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(17L, "Novi apartman 5", "Apartment", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 5", 4, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(18L, "Novi apartman 6", "Studio", "jpg", "", "owner_username", "1703451446", "new", "Lorem ipsum ... opis 6", 5, "jpg", ""));
        accommodationRequest.add(new AccommodationRequest(19L, "Novi apartman 7", "Apartment", "jpg", "", "owner_username", "1703451446", "updated", "Lorem ipsum ... opis 7", 4, "jpg", ""));


        availabilities.add(new Availability(1L, LocalDate.of(2024,10,1), LocalDate.of(2024,10,3),150.00, 1L));
        availabilities.add(new Availability(1L, LocalDate.of(2024,10,4), LocalDate.of(2024,10,6),100.00, 1L));
        availabilities.add(new Availability(1L, LocalDate.of(2024,10,8), LocalDate.of(2024,10,10),150.00, 1L));
        availabilities.add(new Availability(1L, LocalDate.of(2024,10,11), LocalDate.of(2024,10,15),120.00, 1L));
        availabilities.add(new Availability(1L, LocalDate.of(2024,10,29), LocalDate.of(2024,11,2),150.00, 1L));
        availabilities.add(new Availability(1L, LocalDate.of(2024,11,6), LocalDate.of(2024,10,28),150.00, 1L));

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

        accommodationReportDTOS.add(new AccommodationReportDTO("a1",AccommodationType.ROOM,3.5,1,6,150.0,11,10));
        accommodationReportDTOS.add(new AccommodationReportDTO("a2",AccommodationType.STUDIO,3.0,1,6,150.0,10,10));
        accommodationReportDTOS.add(new AccommodationReportDTO("a3",AccommodationType.VILLA,4.5,1,6,150.0,9,10));
        accommodationReportDTOS.add(new AccommodationReportDTO("a4",AccommodationType.ROOM,2.5,1,6,150.0,10,10));
        accommodationReportDTOS.add(new AccommodationReportDTO("a5",AccommodationType.APARTMENT,3.6,1,6,150.0,9,10));




//        NotificationDTO notificationDTO1 = new NotificationDTO(1L, 1L, 2L, "2022-03-01T10:30:00", false, NotificationType.RESERVATION_REQUEST_RESPONSE);
        NotificationDTO notificationDTO = new NotificationDTO(1L, 1L, 2L, "1716558867", false, NotificationType.RESERVATION_REQUEST_RESPONSE);
        ReservationRequestDTO request1 = new ReservationRequestDTO(1L, 1L, 2L, LocalDate.now(), LocalDate.now().plusDays(2), 4, 540.0, RequestStatus.SENT, "Sunny Beach House", "APARTMENT", "jpg", "", "owner1@owner.com", "1716558867", 4, "png", "");
        ReservationRequestDTO request2 = new ReservationRequestDTO(2L, 2L, 3L, LocalDate.now(), LocalDate.now().plusDays(3), 7, 1050.0, RequestStatus.SENT, "Mountain View Cabin", "STUDIO", "jpg", "", "owner2@owner.com", "1716558867", 5, "png", "");
        ReservationRequestDTO request3 = new ReservationRequestDTO(3L, 3L, 4L, LocalDate.now(), LocalDate.now().plusDays(4), 10, 1750.0, RequestStatus.SENT, "Lakeside Villa", "VILLA", "jpg", "", "owner1@owner.com", "1716558867", 4, "png", "");

        guestNotifications.add(new GuestNotificationsData(notificationDTO, request1, true));
        guestNotifications.add(new GuestNotificationsData(notificationDTO, request2, false));
        guestNotifications.add(new GuestNotificationsData(notificationDTO, request3, true));

        accommodationUpdateDTO.setId(1L);
        accommodationUpdateDTO.setName("Sunny Beach House");
        accommodationUpdateDTO.setDescription("Enjoy the sun and sea at this delightful beach house.\nAmazing views!\nGreat location!\nPerfect for a family vacation!\nCome now!");
        accommodationUpdateDTO.setType("APARTMENT");
        accommodationUpdateDTO.setMinNumberOfGuests(2);
        accommodationUpdateDTO.setMaxNumberOfGuests(4);
        HashSet<Image> accommodationUpdateImages = new HashSet<>();
        accommodationUpdateImages.add(new Image("iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAADMElEQVR4nOzVwQnAIBQFQYXff81RUkQCOyDj1YOPnbXWPmeTRef+/3O/OyBjzh3CD95BfqICMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMO0TAAD//2Anhf4QtqobAAAAAElFTkSuQmCC", "jpg"));
        accommodationUpdateImages.add(new Image("iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAADMElEQVR4nOzVwQnAIBQFQYXff81RUkQCOyDj1YOPnbXWPmeTRef+/3O/OyBjzh3CD95BfqICMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMO0TAAD//2Anhf4QtqobAAAAAElFTkSuQmCC", "jpg"));
        accommodationUpdateImages.add(new Image("iVBORw0KGgoAAAANSUhEUgAAAQAAAAEACAIAAADTED8xAAADMElEQVR4nOzVwQnAIBQFQYXff81RUkQCOyDj1YOPnbXWPmeTRef+/3O/OyBjzh3CD95BfqICMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMK0CMO0TAAD//2Anhf4QtqobAAAAAElFTkSuQmCC", "jpg"));
        accommodationUpdateDTO.setImages(accommodationUpdateImages);
        HashSet<Long> accommodationUpdateAmenityIds = new HashSet<>();
        accommodationUpdateAmenityIds.add(2L);
        accommodationUpdateAmenityIds.add(4L);
        accommodationUpdateAmenityIds.add(5L);
        accommodationUpdateAmenityIds.add(7L);
        accommodationUpdateDTO.setAmenities(accommodationUpdateAmenityIds);
        accommodationUpdateDTO.setLocation(new LocationDTO("Serbia","Zlatibor","Katunska 4"));
        accommodationUpdateDTO.setPricePerGuest(false);
        accommodationUpdateDTO.setDefaultPrice(180.0);
        accommodationUpdateDTO.setCancellationDeadline(2);
        accommodationUpdateDTO.setMessage("");
        accommodationUpdateDTO.setAutomaticHandling(false);
        HashSet<UpdateAvailabilityDTO> accommodationUpdateAvailability = new HashSet<>();
        accommodationUpdateAvailability.add(new UpdateAvailabilityDTO(LocalDate.now(), LocalDate.now().plusDays(2), 300.0));
        accommodationUpdateAvailability.add(new UpdateAvailabilityDTO(LocalDate.now().plusDays(3), LocalDate.now().plusDays(3), 320.0));
        accommodationUpdateAvailability.add(new UpdateAvailabilityDTO(LocalDate.now().plusDays(4), LocalDate.now().plusDays(8), 220.0));
        accommodationUpdateDTO.setAvailability(accommodationUpdateAvailability);
    }



    public Map<String, MonthlyAccommodationReport> getMonthlyReportMap() {
        Map<String, MonthlyAccommodationReport> monthlyReportMap = new LinkedHashMap<>();
        Integer year =2024;
        for (int month = 1; month <= 12; month++) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            int numberOfReservations = 10;
            double totalProfit = 233.3;

            MonthlyAccommodationReport monthlyReportDTO = new MonthlyAccommodationReport(startDate.getMonth().toString(), numberOfReservations, totalProfit);
            monthlyReportMap.put(startDate.getMonth().toString(), monthlyReportDTO);
        }
        return monthlyReportMap;
    }
    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public AccommodationUpdateDTO getAccommodationUpdateDTO() {
        return accommodationUpdateDTO;
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



    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public List<AccommodationReportDTO> getAccommodationReports() {
        return accommodationReportDTOS;
    }
}