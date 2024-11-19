package com.gabriele.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.gabriele.common.enums.OrderStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid_order")
    private String uuidOrder;
    @Column(name = "uuid_customer")
    private String uuidCustomer;
    @Column(name = "uuid_item")
    private String uuidItem;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

}