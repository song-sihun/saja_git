package org.lion.restexam.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lion.restexam.domain.Product;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String productName;

    @NotBlank
    @Min(0)
    @Max(1000000)
    private int price;

    public static ProductDTO fromEntity(Product productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setPrice(productEntity.getPrice());
        return productDTO;
    }



}
