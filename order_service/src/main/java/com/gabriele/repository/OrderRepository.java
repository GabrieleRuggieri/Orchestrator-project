package com.gabriele.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.gabriele.entity.Order;

import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE o.uuidOrder = :uuidOrder")
	Mono<Order> findByUuidOrder(String uuidOrder);

	@Query("SELECT o FROM Order o WHERE o.uuidCustomer = :uuidCustomer AND o.uuidItem = :uuidItem")
	Mono<Order> findByUuidCustomerAndUuidItem(String uuidCustomer, String uuidItem);


//    Mono<Order> findByUuidOrder(String uuidOrder);
//
//    Mono<Order> findByUuidCustomerAndUuidItem(String uuidCustomer, String uuidItem);

}