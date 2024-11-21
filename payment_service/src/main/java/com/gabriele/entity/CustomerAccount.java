package com.gabriele.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "CUSTOMER_ACCOUNT")
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid_customer_account")
    private String uuidCustomerAccount;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "state")
    private Boolean state;

}
