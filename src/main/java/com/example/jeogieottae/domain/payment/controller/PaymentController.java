package com.example.jeogieottae.domain.payment.controller;

import com.example.jeogieottae.common.response.GlobalResponse;
import com.example.jeogieottae.domain.payment.dto.ConfirmRequest;
import com.example.jeogieottae.domain.payment.dto.FailPaymentRequest;
import com.example.jeogieottae.domain.payment.dto.RequestPaymentResponse;
import com.example.jeogieottae.domain.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<GlobalResponse<RequestPaymentResponse>> requestPayment(@RequestParam Long reservationId) {

        RequestPaymentResponse response = paymentService.requestPayment(reservationId);
        return ResponseEntity.ok(GlobalResponse.success(true, "결제 요청 성공", response));
    }

    @GetMapping("/success")
    public void successPayment(@ModelAttribute ConfirmRequest request, HttpServletResponse response) throws IOException {

        String redirectUrl = paymentService.successPayment(request);
        response.sendRedirect(redirectUrl);
    }

    @GetMapping("/fail")
    public void failPayment(@ModelAttribute FailPaymentRequest request, HttpServletResponse response) throws IOException {

        String redirectUrl = "/fail.html";
        response.sendRedirect(redirectUrl);
    }
}
