package com.tenpoc.orderservice.controller;

import com.tenpoc.orderservice.dto.OrderRequestDTO;
import com.tenpoc.orderservice.dto.ResponseDTO;
import com.tenpoc.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/place-order")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        orderService.placeOrder(orderRequestDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg("Order Placed Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
