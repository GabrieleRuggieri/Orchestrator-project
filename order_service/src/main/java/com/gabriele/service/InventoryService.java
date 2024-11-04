package com.gabriele.service;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.gabriele.inventory.dto.ItemDTO;

import reactor.core.publisher.Mono;

@Component
public class InventoryService {
	
	@Autowired
	private WebClient webClient;

	public Mono<ItemDTO> getInventoryItem(UUID itemId) {
		 return this.webClient
                 .get()
                 .uri(String.format("/stock/%s", itemId))
                 .retrieve()
                 .bodyToMono(ItemDTO.class)
                 .timeout(Duration.ofMinutes(1l));
	}
}
