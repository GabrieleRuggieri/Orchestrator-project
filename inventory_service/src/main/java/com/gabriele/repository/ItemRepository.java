package com.gabriele.repository;

import com.gabriele.entity.Item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.uuid = :uuidItem AND i.state = true")
    Item findByUuidAndStateTrue(String uuidItem);

}