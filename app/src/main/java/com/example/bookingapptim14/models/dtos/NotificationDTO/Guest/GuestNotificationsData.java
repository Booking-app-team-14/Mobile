package com.example.bookingapptim14.models.dtos.NotificationDTO.Guest;

import com.example.bookingapptim14.enums.NotificationType;
import com.example.bookingapptim14.models.dtos.NotificationDTO.NotificationDTO;

public class GuestNotificationsData extends NotificationDTO {

    private ReservationRequestDTO reservation;
    private Boolean accepted;

    public GuestNotificationsData() { }

    public GuestNotificationsData(NotificationDTO dto, ReservationRequestDTO reservation, Boolean accepted) {
        super(dto.getId(), dto.getSenderId(), dto.getReceiverId(), dto.getSentAt(), dto.isSeen(), dto.getType());
        this.reservation = reservation;
        this.accepted = accepted;
    }

    public GuestNotificationsData(Long id, Long senderId, Long receiverId, String sentAt, boolean seen, NotificationType type, ReservationRequestDTO reservation, Boolean accepted) {
        super(id, senderId, receiverId, sentAt, seen, type);
        this.reservation = reservation;
        this.accepted = accepted;
    }

    public ReservationRequestDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationRequestDTO reservation) {
        this.reservation = reservation;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

}
