package com.tenpoc.productservice.controller;

import com.tenpoc.productservice.dto.ProductDTO;
import com.tenpoc.productservice.dto.ProductRequestDTO;
import com.tenpoc.productservice.dto.ProductResponseDTO;
import com.tenpoc.productservice.dto.ResponseDTO;
import com.tenpoc.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor /*Lombok will generate a controller's constructor for us*/
@RequestMapping(value = "/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        productService.createProduct(productRequestDTO);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMsg("Created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping(value = "/get-all-products")
    public ResponseEntity<ProductResponseDTO> getAllProducts() {
        log.info("Begin: /api/product/get-all-products");
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        List<ProductDTO> productDTOList = productService.getAllProducts();
        productResponseDTO.setProductList(productDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }
}
