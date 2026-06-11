package org.lion.restexam.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lion.restexam.domain.Product;
import org.lion.restexam.dto.ProductDTO;
import org.lion.restexam.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void list() {
        ResponseEntity<List<ProductDTO>> response = productController.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("productName");
        productDTO.setPrice(1000);

        productController.create(productDTO);
        ResponseEntity<List<ProductDTO>> response = productController.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void update() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("productName");
        productDTO.setPrice(1000);

        ResponseEntity<ProductDTO> productDTOResponseEntity = productController.create(productDTO);
        assertNotNull(productDTOResponseEntity.getBody());
        Long id = productDTOResponseEntity.getBody().getId();
        productDTO.setProductName("updatedProductName");
        productDTO.setPrice(10000);
        ResponseEntity<ProductDTO> updated = productController.update(id, productDTO);
        assertEquals(HttpStatus.OK, updated.getStatusCode());

    }

    @Test
    void delete() {
    }
}