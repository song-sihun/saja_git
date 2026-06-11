package org.lion.restexam.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.lion.restexam.dto.ProductDTO;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String productName="temp product name";
    @Builder.Default
    private int price=0;

    public Product(String productName, int price) {
        this.productName = productName;
        this.price = price;
    }

    public static Product fromDTO(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .price(productDTO.getPrice())
                .build();
    }

}
