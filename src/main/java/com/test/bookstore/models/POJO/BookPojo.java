package com.test.bookstore.models.POJO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * Modelo utilizado para realizar el patch sin validar campos nulos en caso de que no
 * vengan en el request.
 */

@Getter
@Setter
public class BookPojo {
    private String title;
    private String description;
    private Integer stock;
    private BigDecimal salePrice;
    private Boolean available;
}
