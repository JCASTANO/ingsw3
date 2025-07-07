package com.uco.myproject.aplicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DtoOrderRequest {
    
    private Long customerId;
    private List<DtoOrderItemRequest> items;
    private String paymentMethod;
}