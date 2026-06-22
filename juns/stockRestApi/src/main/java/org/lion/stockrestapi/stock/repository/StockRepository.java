package org.lion.stockrestapi.stock.repository;

import org.lion.stockrestapi.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
