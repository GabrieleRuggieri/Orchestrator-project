package com.patroclos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "uuid", unique = true)
    private UUID uuid;
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "stock_available")
    private Integer stockAvailable;
}
