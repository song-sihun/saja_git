package sample.exam.dao;

import sample.exam.domain.Product;

import java.util.List;

public class ProductDaoimpl implements ProductDao {
    @Override
    public void save(Product product) {
        System.out.println(product+" saved");
    }

    @Override
    public void update(Product product) {
        System.out.println(product+" updated");

    }

    @Override
    public void delete(Product product) {
        System.out.println(product+" deleted");

    }

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product findById(long id) {
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        return List.of();
    }
}
