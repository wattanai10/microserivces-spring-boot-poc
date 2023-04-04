package com.tenpoc.productservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
