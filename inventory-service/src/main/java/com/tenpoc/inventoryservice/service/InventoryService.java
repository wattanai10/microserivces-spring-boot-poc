package com.tenpoc.inventoryservice.service;

import com.tenpoc.inventoryservice.dto.InventoryDTO;
import com.tenpoc.inventoryservice.model.Inventory;
import com.tenpoc.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryDTO> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(this::mapToInventoryDTO).collect(Collectors.toList());
    }

    public InventoryDTO mapToInventoryDTO(Inventory inventory) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setInStock(inventory.getQuantity() > 0);
        inventoryDTO.setSkuCode(inventory.getSkuCode());
        return inventoryDTO;
    }
}
