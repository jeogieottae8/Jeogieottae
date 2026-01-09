package com.example.jeogieottae.domain.reservation.dto;

import com.example.jeogieottae.domain.reservation.entity.Reservation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationPaymentResponse {

    private final Long reservationId;
    private final String name;
    private final String accommodationName;
    private final Long price;

    public static ReservationPaymentResponse from(Reservation reservation){

        return new ReservationPaymentResponse(
        reservation.getId(),
        reservation.getUser().getUsername(),
        reservation.getRoom().getAccommodation().getName(),
        reservation.getDiscountedPrice());
    }
}
