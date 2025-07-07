package com.uco.myproject.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DtoOrderItemResponse {
    
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}