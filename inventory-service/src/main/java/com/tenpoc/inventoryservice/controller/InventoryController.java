package com.tenpoc.inventoryservice.controller;

import com.tenpoc.inventoryservice.dto.InventoryDTO;
import com.tenpoc.inventoryservice.dto.InventoryResponseDTO;
import com.tenpoc.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/status")
    public ResponseEntity<List<InventoryDTO>> isInStock(@RequestParam List<String> skuCode) {
        log.info("Begin: /api/inventory/status");
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.isInStock(skuCode));
    }
}
