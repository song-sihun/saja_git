package sample.exam.config;

import sample.exam.controller.ProductController;
import sample.exam.dao.ProductDao;
import sample.exam.dao.ProductDaoimpl;
import sample.exam.service.ProductService;
import sample.exam.service.ProductServiceimpl;
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
