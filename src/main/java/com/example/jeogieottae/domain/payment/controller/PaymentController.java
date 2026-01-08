package com.example.jeogieottae.domain.payment.controller;

import com.example.jeogieottae.common.dto.AuthUser;
import com.example.jeogieottae.domain.payment.dto.ConfirmRequest;
import com.example.jeogieottae.domain.payment.dto.FailPaymentRequest;
import com.example.jeogieottae.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    @Value("${spring.payment.secret-key}")
    private String secretKey;

    @Value("${spring.payment.base-url}")
    private String baseUrl;

    private final PaymentService paymentService;

    @PostMapping("/request")
    public String requestPayment(@AuthenticationPrincipal AuthUser authUser, @RequestParam Long reservationId) {

        String redirectUrl = paymentService.requestPayment(authUser.getUserId(), reservationId);
        return redirectUrl;
    }

    @GetMapping("/payments/success")
    public String successPayment(@ModelAttribute ConfirmRequest request) {

        String redirectUrl = paymentService.successPayment(request);

        return redirectUrl;
    }

    @GetMapping("/payments/fail")
    public String failPayment(@ModelAttribute FailPaymentRequest request) {

        return "redirect:/fail.html";
    }
}
