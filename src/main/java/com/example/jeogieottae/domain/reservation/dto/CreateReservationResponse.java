package com.example.jeogieottae.domain.reservation.dto;

import com.example.jeogieottae.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateReservationResponse {
    private final String userName;
    private final String accommodationName;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final Long guest;
    private final Long discountPrice;

    public CreateReservationResponse(ReservationDto dto, User user, Long discountPrice) {

        this.userName = user.getUsername();
        this.accommodationName = null;
        this.checkIn = dto.getCheckIn();
        this.checkOut = dto.getCheckOut();
        this.guest = dto.getGuestCount();
        this.discountPrice = discountPrice;
    }
}
