package com.tenpoc.orderservice.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private Long id;
    private String skuCode;
    private boolean inStock;
}
