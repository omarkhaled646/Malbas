package com.aden.malbas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    @NotNull @NotEmpty @NotBlank
    private String name;
    private String collection;
    private String description;
    @NotNull @Min(1)
    private Double price;
    @NotNull @Min(0)
    private Integer availablePiecesCount;
    @NotNull @NotEmpty
    private List<String> availableColors;
    private Boolean isOnSale = false;
    @Min(1)
    private Double salePrice;


}