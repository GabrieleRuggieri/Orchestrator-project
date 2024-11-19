package com.gabriele.service;

import com.gabriele.common.dto.ItemRequestDTO;
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

    public ItemRequestDTO createNewItem(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();
        item.setUuid(UUID.randomUUID().toString());
        item.setStockAvailable(itemRequestDTO.getStockAvailable());
        item.setPrice(itemRequestDTO.getPrice());
        item.setState(itemRequestDTO.getState());
        itemRepository.save(item);
        return itemRequestDTO;
    }


    public InventoryResponseDTO deductInventory(InventoryRequestDTO requestDTO) {
        Item item = itemRepository.findByUuidAndStateTrue(requestDTO.getUuidItem());

        if (item == null)
            throw new ItemNotFoundException();

        InventoryResponseDTO responseDTO = new InventoryResponseDTO();
        responseDTO.setUuidOrder(requestDTO.getUuidOrder());
        responseDTO.setUuidCustomer(requestDTO.getUuidCustomer());
        responseDTO.setUuidItem(requestDTO.getUuidItem());

        if (item.getStockAvailable() > 0) {
            responseDTO.setStatus(InventoryStatus.INSTOCK);
            item.setStockAvailable(item.getStockAvailable() - 1);
            itemRepository.save(item);
            log.info("Item with id {} deducted from stock", requestDTO.getUuidItem());
        } else {
            responseDTO.setStatus(InventoryStatus.OUTOFSTOCK);
            log.info("Item with id {} is out of stock", requestDTO.getUuidItem());
        }

        return responseDTO;
    }

    public void addInventory(InventoryRequestDTO requestDTO) {
        Item item = itemRepository.findByUuidAndStateTrue(requestDTO.getUuidItem());
        if (item == null)
            throw new ItemNotFoundException();

        item.setStockAvailable(item.getStockAvailable() + 1);
        itemRepository.save(item);

        log.info("Stock was updated as +1 availability for item with uuid {}", item.getUuid());
    }

    public ItemDTO getItem(String uuidItem) {
        return getItemDTO(itemRepository.findByUuidAndStateTrue(uuidItem));
    }

    private ItemDTO getItemDTO(Item i) {
        return modelMapper.map(i, ItemDTO.class);
    }


}
