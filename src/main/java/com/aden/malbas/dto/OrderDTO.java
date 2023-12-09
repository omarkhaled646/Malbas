package com.aden.malbas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {

    @NotNull
    private Integer id;
    @NotNull
    private String status;
    @Min(1)
    private Double totalCost;
}
