package com.test.bookstore.models.POJO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPojo {
    private String title;
    private String description;
    private Integer stock;
    private BigDecimal salePrice;
    private Boolean available;
}
