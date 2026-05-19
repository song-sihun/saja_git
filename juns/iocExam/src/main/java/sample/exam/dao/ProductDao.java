package sample.exam.dao;

import sample.exam.domain.Product;

import java.util.List;

public interface ProductDao {
    public void save(Product product);
    public void update(Product product);
    public void delete(Product product);
    public List<Product> findAll();
    public Product findById(long id);
    public List<Product> findByName(String name);
}
