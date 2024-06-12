package com.example.bookingapptim14.models.dtos.NotificationDTO.Host;

import com.example.bookingapptim14.enums.NotificationType;
import com.example.bookingapptim14.models.dtos.NotificationDTO.Guest.ReservationRequestDTO;
import com.example.bookingapptim14.models.dtos.NotificationDTO.NotificationDTO;

public class HostNotificationsData extends NotificationDTO {

    private com.example.bookingapptim14.models.dtos.NotificationDTO.Host.ReservationRequestDTO reservation;

    public HostNotificationsData() { }

    public HostNotificationsData(NotificationDTO dto, com.example.bookingapptim14.models.dtos.NotificationDTO.Host.ReservationRequestDTO reservation, Boolean accepted) {
        super(dto.getId(), dto.getSenderId(), dto.getReceiverId(), dto.getSentAt(), dto.isSeen(), dto.getType());
        this.reservation = reservation;
    }

    public HostNotificationsData(Long id, Long senderId, Long receiverId, String sentAt, boolean seen, NotificationType type, com.example.bookingapptim14.models.dtos.NotificationDTO.Host.ReservationRequestDTO reservation, Boolean accepted) {
        super(id, senderId, receiverId, sentAt, seen, type);
        this.reservation = reservation;

    }

    public com.example.bookingapptim14.models.dtos.NotificationDTO.Host.ReservationRequestDTO getReservation() {
        return reservation;
    }

    public void setReservation(com.example.bookingapptim14.models.dtos.NotificationDTO.Host.ReservationRequestDTO reservation) {
        this.reservation = reservation;
    }


}
