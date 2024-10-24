package com.gabriele.repository;

import com.gabriele.entity.Item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.uuidItem = :uuidItem AND i.state = true")
    Mono<Item> findByUuidAndStateTrue(String uuidItem);

}