package org.example.iocexam.config;

import org.example.iocexam.controller.ProductController;
import org.example.iocexam.dao.ProductDao;
import org.example.iocexam.dao.ProductDaoimpl;
import org.example.iocexam.dao.UserDao;
import org.example.iocexam.domain.Product;
import org.example.iocexam.service.ProductService;
import org.example.iocexam.service.ProductServiceimpl;
import org.example.iocexam.service.UserService;
import org.springframework.context.annotation.Bean;

public class ProductConfig {

    @Bean
    public ProductDao productDao() {
        return new ProductDaoimpl();
    }

    @Bean
    public ProductService productService(ProductDao productDao) {
        return new ProductServiceimpl(productDao);
    }

    @Bean
    public ProductController productController(ProductService productService) {
        return new ProductController(productService);
    }
}
