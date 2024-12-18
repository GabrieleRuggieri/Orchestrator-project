package com.gabriele.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gabriele.common.enums.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("ORDERS")
public class Order {

    @Id
    @Column("id")
    private Long id;

    @Column("uuid_order")
    private String uuidOrder;

    @Column("uuid_customer")
    private String uuidCustomerAccount;

    @Column("uuid_item")
    private String uuidItem;

    @Column("price")
    private BigDecimal price;

    @Column("status")
    private OrderStatus status;

    @Column("creation_date")
    private LocalDateTime creationDate;
}
