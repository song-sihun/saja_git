package org.lion.restexam.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.restexam.domain.Product;
import org.lion.restexam.dto.ProductDTO;
import org.lion.restexam.repository.MemoRepository;
import org.lion.restexam.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found product"));
        return ProductDTO.fromEntity(product);
    }

    public List<ProductDTO> findAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductDTO::fromEntity)
                .toList();
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO){
        Product product = Product.fromDTO(productDTO);
        productRepository.save(product);
        return ProductDTO.fromEntity(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found product"));

//        product.setProductName(productDTO.getProductName());
//        product.setPrice(productDTO.getPrice());

        Optional.ofNullable(product.getProductName())
                .ifPresent(productDTO::setProductName);
//        Optional.of(product.getPrice())
//                .ifPresent(product::setPrice);

        if (productDTO.getPrice() != 0){
            product.setPrice(productDTO.getPrice());
        }

        return ProductDTO.fromEntity(product);
    }

    @Transactional
    public void delete(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found product"));
        productRepository.delete(product);
    }
}
