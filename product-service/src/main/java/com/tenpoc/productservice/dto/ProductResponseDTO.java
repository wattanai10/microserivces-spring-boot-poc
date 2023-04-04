package com.tenpoc.productservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponseDTO {
    private List<ProductDTO> productList;
}
