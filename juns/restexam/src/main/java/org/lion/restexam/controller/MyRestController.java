//package org.lion.restexam.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.lion.restexam.dto.ProductDTO;
//import org.lion.restexam.dto.User;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
//public class MyRestController {
//    @GetMapping(value = "/hi", produces = "application/xml")
//    public String hi(){
//        return "hi";
//    }
//
//    @GetMapping("/greeting")
//    public Map<String, String > greeting(@RequestParam(name = "name", required = false, defaultValue = "guest") String name){
//        Map<String,String> response = new HashMap<>();
//        response.put("name",name);
//        return response;
//    }
//
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user){
//        user.setId(1L);
//        user.setCreatedAt(LocalDateTime.now());
//        return user;
//    }
//
//    @GetMapping("/products")
//    public List<ProductDTO> getProducts(){
//        List<ProductDTO> products = new ArrayList<>();
//        products.add(new ProductDTO("product1", 1000));
//        products.add(new ProductDTO("product2", 2000));
//        return products;
//    }
//
//
//    @PostMapping("/product/add")
//    public ProductDTO addProduct(@RequestBody ProductDTO productDTO){
//        return new ProductDTO(productDTO.getProductName(),productDTO.getPrice());
//    }
//
//    @PutMapping("/products/{id}")
//    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
//        return productDTO;
//    }
//
//    @DeleteMapping("/products/{id}/delete")
//    public void deleteProduct(@PathVariable Long id){
//        log.info("delete product id:{}",id);
//    }
//
//
//    @GetMapping("/custom")
//    public ResponseEntity<ProductDTO> getCustom(){
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(new ProductDTO("product1", 1000));
//    }
//
//
//
//}
