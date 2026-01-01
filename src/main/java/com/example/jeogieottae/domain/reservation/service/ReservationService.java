package com.example.jeogieottae.domain.reservation.service;

import com.example.jeogieottae.common.exception.CustomException;
import com.example.jeogieottae.common.exception.ErrorCode;
import com.example.jeogieottae.domain.reservation.dto.CreateReseravtionRequest;
import com.example.jeogieottae.domain.reservation.dto.CreateReservationResponse;
import com.example.jeogieottae.domain.reservation.dto.ReservationDto;
import com.example.jeogieottae.domain.reservation.entity.Reservation;
import com.example.jeogieottae.domain.reservation.repository.ReservationRepository;
import com.example.jeogieottae.domain.room.entity.Room;
import com.example.jeogieottae.domain.room.repository.RoomRepository;
import com.example.jeogieottae.domain.user.entity.User;
import com.example.jeogieottae.domain.usercoupon.entity.UserCoupon;
import com.example.jeogieottae.domain.usercoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public CreateReservationResponse createReservation(
            Long roomId,
            Long userCouponId,
            User user,
            CreateReseravtionRequest request
    ) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId).orElseThrow(
                () -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        if (userCoupon.isUsed()) {
            throw new CustomException(ErrorCode.COUPON_ALREADY_ISSUED);
        }

        Boolean ableToReservationFlag = reservationRepository.ableToReservation(
                user.getId(),
                roomId,
                request.getGuest(),
                userCouponId,
                request.getCheckIn(),
                request.getCheckOut()
        );

        if (!ableToReservationFlag) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_AVAILABLE);
        }

        Room room = roomRepository.getReferenceById(roomId);
        ReservationDto dto = new ReservationDto(Reservation.create(user, room, userCouponId, request));
        Long discountPrice = room.getPrice() * (100 - userCoupon.getCoupon().getDiscountValue()) / 100;

        return new CreateReservationResponse(dto, user, discountPrice);
    }
}
