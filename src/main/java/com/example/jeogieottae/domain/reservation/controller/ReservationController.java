package com.example.jeogieottae.domain.reservation.controller;

import com.example.jeogieottae.common.response.GlobalResponse;
import com.example.jeogieottae.domain.reservation.dto.CreateReseravtionRequest;
import com.example.jeogieottae.domain.reservation.dto.CreateReservationResponse;
import com.example.jeogieottae.domain.reservation.service.ReservationService;
import com.example.jeogieottae.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{roomId}/{couponId}")
    public ResponseEntity<GlobalResponse<CreateReservationResponse>> createReservation(
            @AuthenticationPrincipal User user,
            @PathVariable Long roomId,
            @PathVariable Long couponId,
            @ModelAttribute CreateReseravtionRequest request
    ) {
        CreateReservationResponse response = reservationService.createReservation(roomId, couponId, user, request);
        return ResponseEntity.ok(GlobalResponse.success(true, "숙소 예약 성공", response));
    }
}
