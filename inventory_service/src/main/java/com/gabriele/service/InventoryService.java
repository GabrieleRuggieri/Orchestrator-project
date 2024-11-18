package com.gabriele.service;

import com.gabriele.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriele.common.dto.InventoryRequestDTO;
import com.gabriele.common.dto.InventoryResponseDTO;
import com.gabriele.common.enums.InventoryStatus;
import com.gabriele.configuration.GlobalModelMapper;
import com.gabriele.entity.Item;
import com.gabriele.inventory.dto.ItemDTO;
import com.gabriele.repository.ItemRepository;

import java.util.UUID;

@Slf4j
@Service
public class InventoryService {

    @Autowired
    private ItemRepository itemRepository;

    private final ModelMapper modelMapper = GlobalModelMapper.getModelMapper();

    public Item createNewItem(String itemName, int initialStock) {
        Item item = new Item();
        item.setUuid(UUID.randomUUID().toString());
        item.setStockAvailable(initialStock);
        item.setState(true);
        itemRepository.save(item).block();
        return item;
    }


    public InventoryResponseDTO deductInventory(InventoryRequestDTO requestDTO) {
        Item item = itemRepository.findByUuidAndStateTrue(requestDTO.getUuidItem()).block();

        if (item == null)
            throw new ItemNotFoundException();

        InventoryResponseDTO responseDTO = new InventoryResponseDTO();
        responseDTO.setUuidOrder(requestDTO.getUuidOrder());
        responseDTO.setUuidCustomer(requestDTO.getUuidCustomer());
        responseDTO.setUuidItem(requestDTO.getUuidItem());

        if (item.getStockAvailable() > 0) {
            responseDTO.setStatus(InventoryStatus.INSTOCK);
            item.setStockAvailable(item.getStockAvailable() - 1);
            itemRepository.save(item).block();
            log.info("Item with id {} deducted from stock", requestDTO.getUuidItem());
        } else {
            responseDTO.setStatus(InventoryStatus.OUTOFSTOCK);
            log.info("Item with id {} is out of stock", requestDTO.getUuidItem());
        }

        return responseDTO;
    }

//    public void addInventory(InventoryRequestDTO requestDTO) {
//        Item item = itemRepository.findByUuidAndStateTrue(requestDTO.getUuidItem()).block();
//        if (item == null)
//            throw new ItemNotFoundException();
//
//        item.setStockAvailable(item.getStockAvailable() + 1);
//        itemRepository.save(item).block();
//
//        log.info("Stock was updated as +1 availability for item with uuid {}", item.getUuid());
//    }

    public void addInventory(InventoryRequestDTO requestDTO) {
        Item item = itemRepository.findByUuidAndStateTrue(requestDTO.getUuidItem()).block();

        if (item == null) {
            item = new Item();
            item.setUuid(UUID.randomUUID().toString());
            item.setState(true);
            itemRepository.save(item).block();
            log.info("New item created with UUID {}", item.getUuid());
        } else {
            itemRepository.save(item).block();
            log.info("Stock updated for existing item with UUID {}", item.getUuid());
        }
    }


    public ItemDTO getItem(String uuidItem) {
        return getItemDTO(itemRepository.findByUuidAndStateTrue(uuidItem).block());
    }

    private ItemDTO getItemDTO(Item i) {
        return modelMapper.map(i, ItemDTO.class);
    }


}
