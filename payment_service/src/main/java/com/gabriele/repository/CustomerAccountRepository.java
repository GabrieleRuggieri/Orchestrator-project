package com.gabriele.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gabriele.entity.CustomerAccount;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {

    @Query("SELECT c FROM CustomerAccount c WHERE c.uuidCustomerAccount = :uuidCustomer AND c.state = true")
    CustomerAccount findByCustomerUuidAndStateTrue(String uuidCustomer);
}
