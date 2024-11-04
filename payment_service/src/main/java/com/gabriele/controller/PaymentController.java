package com.gabriele.controller;

import com.gabriele.common.dto.PaymentRequestDTO;
import com.gabriele.common.dto.PaymentResponseDTO;
import com.gabriele.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService service;
    
    @PostMapping("/debitAccount")
    public PaymentResponseDTO debitAccount(@RequestBody final PaymentRequestDTO requestDTO){
       return this.service.debitAccount(requestDTO);
    }

    @PostMapping("/creditAccount")
    public void creditAccount(@RequestBody final PaymentRequestDTO requestDTO){
        this.service.creditAccount(requestDTO);
    }

}
