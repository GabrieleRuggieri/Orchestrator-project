package com.gabriele.controller;

import com.gabriele.common.dto.InventoryRequestDTO;
import com.gabriele.common.dto.InventoryResponseDTO;
import com.gabriele.common.dto.ItemRequestDTO;
import com.gabriele.inventory.dto.ItemDTO;
import com.gabriele.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/items")
    public List<ItemRequestDTO> createItems(@RequestBody List<ItemRequestDTO> itemRequestDTO) {
        return this.inventoryService.createNewItems(itemRequestDTO);
    }

    @GetMapping("/items/{uuidItem}")
    public ItemDTO getItem(@PathVariable String uuidItem) {
        return this.inventoryService.getItem(uuidItem);
    }

    @PostMapping("/deduct")
    public InventoryResponseDTO deduct(@RequestBody InventoryRequestDTO requestDTO) {
        return this.inventoryService.deductInventory(requestDTO);
    }

    @PostMapping("/add")
    public void add(@RequestBody InventoryRequestDTO requestDTO) {
        this.inventoryService.addInventory(requestDTO);
    }

}
