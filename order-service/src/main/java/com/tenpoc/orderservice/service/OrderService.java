package com.tenpoc.orderservice.service;

import com.tenpoc.orderservice.config.WebClientConfig;
import com.tenpoc.orderservice.dto.InventoryDTO;
import com.tenpoc.orderservice.dto.InventoryResponseDTO;
import com.tenpoc.orderservice.dto.OrderLineItemsDTO;
import com.tenpoc.orderservice.dto.OrderRequestDTO;
import com.tenpoc.orderservice.model.Order;
import com.tenpoc.orderservice.model.OrderLineItems;
import com.tenpoc.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequestDTO.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToOrderLineItems)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodeList = orderLineItemsList
                .stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        InventoryDTO[] result = webClientBuilder.build().get()
                .uri("http://inventory-service/pocmicroservice/api/inventory/status", /*call inventory service by application name that was registered to discover server*/
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(InventoryDTO[].class)//Define data type of return value
                .block(); //synchronous request

        if (result == null || result.length <= 0) {
            throw new IllegalArgumentException("Product is not in found.");
        }

        boolean allProductsInStock = Arrays
                .stream(result)
                .allMatch(InventoryDTO::isInStock); //return true if all isInStock values of product are True

        if (allProductsInStock) {
            log.info("Begin save order");
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    public OrderLineItems mapToOrderLineItems(OrderLineItemsDTO dto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(dto.getPrice());
        orderLineItems.setQuantity(dto.getQuantity());
        orderLineItems.setSkuCode(dto.getSkuCode());
        return orderLineItems;
    }
}
