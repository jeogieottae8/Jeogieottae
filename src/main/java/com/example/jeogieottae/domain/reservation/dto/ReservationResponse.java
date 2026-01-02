package com.example.jeogieottae.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationResponse {
    private final String name;
    private final Long price;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
}
