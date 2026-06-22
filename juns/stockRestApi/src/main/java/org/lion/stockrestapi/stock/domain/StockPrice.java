package org.lion.stockrestapi.stock.domain;

import org.lion.stockrestapi.global.domain.BaseDateEntity;

public class StockPrice extends BaseDateEntity {
    private Long id;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Long volume;

}
