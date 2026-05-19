package sample.exam.service;

import sample.exam.dao.ProductDao;
import sample.exam.domain.Product;

import java.util.List;

public class ProductServiceimpl implements ProductService{
    private final ProductDao productDao;
    public ProductServiceimpl(ProductDao productDao) {
        this.productDao = productDao;
    }
    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(Product product) {
        productDao.delete(product);
    }

    @Override
    public Product findById(long id) {
        if (productDao.findById(id) == null) {
            return null;
        }
        return productDao.findById(id);
    }

    @Override
    public List<Product> findByName(String name) {
        if (productDao.findByName(name) == null) {
            return null;
        }
        return productDao.findByName(name);
    }
}
