package com.tenpoc.productservice.service;

import com.tenpoc.productservice.dto.ProductDTO;
import com.tenpoc.productservice.dto.ProductRequestDTO;
import com.tenpoc.productservice.model.Product;
import com.tenpoc.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final WebClient webClient;

    public ProductService(ProductRepository productRepository,
                          WebClient webClient) {
        this.productRepository = productRepository;
        this.webClient = webClient;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Execute createProduct method");
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());

        product = productRepository.save(product);
        log.info("Save product (id: {})", product.getId());
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponseDTO).collect(Collectors.toList());
    }

    //mapper
    private ProductDTO mapToProductResponseDTO(Product product) {
        ProductDTO responseDTO = new ProductDTO();
        responseDTO.setId(product.getId());
        responseDTO.setName(product.getName());
        responseDTO.setPrice(product.getPrice());
        responseDTO.setDescription(product.getDescription());
        return responseDTO;
    }

}
