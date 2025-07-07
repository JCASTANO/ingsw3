package com.uco.myproject.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DtoOrderResponse {
    
    private Long orderId;
    private Long customerId;
    private List<DtoOrderItemResponse> items;
    private BigDecimal totalAmount;
    private String status;
    private String paymentMethod;
}