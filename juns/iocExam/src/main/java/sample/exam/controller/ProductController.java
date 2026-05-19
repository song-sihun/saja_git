package sample.exam.controller;

import sample.exam.domain.Product;
import sample.exam.service.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    public Product findById(int id) {
        return productService.findById(id);
    }
    public List<Product> findByName(String name) {
        return productService.findByName(name);
    }

    public void save() {
        Product product1 = new Product();
        product1.setId(1);
        product1.setProductName("product1");
        product1.setProductCategory("product1 category");
        product1.setProductDescription("product1 description");
        product1.setProductPrice(10000.0);
        product1.setProductStock(100);
        product1.setProductOwner("product owner");
        productService.save(product1);
    }
}
