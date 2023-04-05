package com.tenpoc.orderservice.controller;

import com.tenpoc.orderservice.dto.OrderRequestDTO;
import com.tenpoc.orderservice.dto.ResponseDTO;
import com.tenpoc.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/order")
public class OrderController {

    private final OrderService orderService;

    /*
    * @CircuitBreaker
    * name -> map Resilience4J CircuitBreaker properties in application.yml by name ane use to show in actuator health
    * fallbackMethod -> a fallback method to be called when the circuit is open
    * */
    @PostMapping(value = "/place-order")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackPlaceOrder")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<ResponseDTO>> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return CompletableFuture.supplyAsync(() -> placeOrderTimeLimiter(orderRequestDTO));
    }

    private ResponseEntity<ResponseDTO> placeOrderTimeLimiter(OrderRequestDTO orderRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg(orderService.placeOrder(orderRequestDTO));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    /* need to return the same data type with the controller */
    @SuppressWarnings("unused")
    public CompletableFuture<ResponseEntity<ResponseDTO>> fallBackPlaceOrder(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg("FALL BACK!");
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO));
    }

    @SuppressWarnings("unused")
    public CompletableFuture<ResponseEntity<ResponseDTO>> fallBackPlaceOrder2(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg("FALL BACK!");
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO));
    }
}
