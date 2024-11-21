package com.gabriele.service;

import com.gabriele.common.dto.CustomerAccountDTO;
import com.gabriele.common.dto.CustomerAccountRequestDTO;
import com.gabriele.common.dto.PaymentRequestDTO;
import com.gabriele.common.dto.PaymentResponseDTO;
import com.gabriele.common.enums.PaymentStatus;
import com.gabriele.entity.CustomerAccount;
import com.gabriele.repository.CustomerAccountRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    public CustomerAccountDTO creationCustomerAccount(CustomerAccountRequestDTO requestDTO) {

        String uuidCustomerAccount = UUID.randomUUID().toString();

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setUuidCustomerAccount(uuidCustomerAccount);
        customerAccount.setBalance(requestDTO.getBalance());
        customerAccount.setState(requestDTO.getState());

        customerAccountRepository.save(customerAccount);

        CustomerAccountDTO responseDTO = new CustomerAccountDTO();
        responseDTO.setUuidCustomerAccount(uuidCustomerAccount);
        responseDTO.setBalance(requestDTO.getBalance());
        responseDTO.setState(requestDTO.getState());

        log.info("New customer account created with UUID: {}, balance: {}, state: {}", uuidCustomerAccount, requestDTO.getBalance(), requestDTO.getState());

        return responseDTO;

    }

    public PaymentResponseDTO debitAccount(PaymentRequestDTO requestDTO) {

        CustomerAccount customer = customerAccountRepository.findByCustomerUuidAndStateTrue(requestDTO.getUuidCustomerAccount());

        if (customer == null) {
            throw new RuntimeException("Customer account not found");
        }

        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setUuidCustomerAccount(requestDTO.getUuidCustomerAccount());
        responseDTO.setUuidOrder(requestDTO.getUuidOrder());
        responseDTO.setStatus(PaymentStatus.REJECTED);

        if (customer.getBalance().compareTo(requestDTO.getAmount()) >= 0) {
            responseDTO.setStatus(PaymentStatus.APPROVED);
            customer.setBalance(customer.getBalance().subtract(requestDTO.getAmount()));
            customerAccountRepository.save(customer);
            log.info("Customer account with uuidCustomer: {} , debited with the amount of: {} euros", customer.getUuidCustomerAccount(), requestDTO.getAmount());
        } else
            log.info("Customer account with uuidCustomer: {} ,not enough funds: {}  ,to be debited with the amount of {} euros", customer.getUuidCustomerAccount(), customer.getBalance(), requestDTO.getAmount());

        return responseDTO;
    }

    public void creditAccount(PaymentRequestDTO requestDTO) {

        CustomerAccount customer = customerAccountRepository.findByCustomerUuidAndStateTrue(requestDTO.getUuidCustomerAccount());

        if (customer == null)
            throw new RuntimeException("Customer account not found");

        customer.setBalance(customer.getBalance().add(requestDTO.getAmount()));
        customerAccountRepository.save(customer);

        log.info("Customer account with uuidCustomer: {} credited with the amount of: {}  euros", customer.getUuidCustomerAccount(), requestDTO.getAmount());
    }

}
