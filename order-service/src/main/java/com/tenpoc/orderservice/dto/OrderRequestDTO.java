package com.tenpoc.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private List<OrderLineItemsDTO> orderLineItemsDTOList;
}
