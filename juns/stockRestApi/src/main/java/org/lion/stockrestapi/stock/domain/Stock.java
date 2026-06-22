package org.lion.stockrestapi.stock.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.stockrestapi.global.domain.BaseDateEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stocks")
public class Stock extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ticker;

    @Column(nullable = false)
    private String company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "stock_price_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id")
    )
    private List<StockPrice> prices = new ArrayList<>();

}
