package sample.exam.service;

import sample.exam.domain.Product;

import java.util.List;

public interface ProductService {
    public void save(Product product);
    public void update(Product product);
    public void delete(Product product);
    public Product findById(long id);
    public List<Product> findByName(String name);

}
