package com.example.jeogieottae.domain.payment.controller;

import com.example.jeogieottae.domain.payment.dto.ConfirmRequest;
import com.example.jeogieottae.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    @Value("${payment.secret.key}")
    private String secretKey;

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody ConfirmRequest request) {


//        WebClient client = WebClient.builder()
//                .baseUrl("https://api.tosspayments.com/v1/payments/confirm")
//                .defaultHeader(HttpHeaders.AUTHORIZATION,
//                        "Basic " + Base64.getEncoder().encodeToString((secretKey + ":").getBytes()))
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//
        return ResponseEntity.ok("response");
    }

}
