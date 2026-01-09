package com.example.jeogieottae.domain.payment.service;

import com.example.jeogieottae.common.exception.CustomException;
import com.example.jeogieottae.common.exception.ErrorCode;
import com.example.jeogieottae.domain.payment.dto.ConfirmRequest;
import com.example.jeogieottae.domain.payment.dto.RequestPaymentResponse;
import com.example.jeogieottae.domain.reservation.entity.Reservation;
import com.example.jeogieottae.domain.reservation.enums.ReservationPayment;
import com.example.jeogieottae.domain.reservation.repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${spring.payment.secret-key}")
    private String secretKey;

    @Value("${spring.payment.base-url}")
    private String baseUrl;

    private final ReservationRepository reservationRepository;
    private final WebClient webClient = WebClient.builder().build();

    @Transactional
    public RequestPaymentResponse requestPayment(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND)
        );

        if (reservation.getPayment().equals(ReservationPayment.SUCCESS)) {
            throw new CustomException(ErrorCode.PAID_RESERVATION);
        }

        if (reservation.getPaymentDeadline().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.PAYMENT_NOT_AVAILABLE);
        }

        String paymentUrl = "http://localhost:8080/payment_page.html?reservationId=" + reservationId;
        return RequestPaymentResponse.from(paymentUrl);
    }

    @Transactional
    public String successPayment(ConfirmRequest request) throws JsonProcessingException {

        String response = webClient.post()
                .uri(baseUrl + "/confirm")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(response);

        String status = json.get("status").asText();
        String orderId = json.get("orderId").asText();
        Long amount = json.get("amount").asLong();

        if (!"DONE".equals(status)) {
            throw new CustomException(ErrorCode.PAYMENT_NOT_AVAILABLE);
        }

        Long reservationId = Long.valueOf(orderId.split("-")[0]);

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND)
        );

        if (!amount.equals(reservation.getDiscountedPrice())) {
            throw new CustomException(ErrorCode.AMOUNT_NOT_MATCH);
        }

        if (reservation.getPayment().equals(ReservationPayment.SUCCESS)) {
            throw new CustomException(ErrorCode.PAID_RESERVATION);
        }
        reservation.setPayment(ReservationPayment.SUCCESS);
        reservationRepository.flush();

        String redirectUrl = "/success.html";

        return redirectUrl;
    }
}
