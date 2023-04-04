package com.tenpoc.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineItemsDTO {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
