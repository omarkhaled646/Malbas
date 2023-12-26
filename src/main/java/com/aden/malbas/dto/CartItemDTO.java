package com.aden.malbas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDTO {

    @NotNull @NotEmpty @NotBlank
    private String itemName;
    @NotNull @NotEmpty @NotBlank
    private String itemDescription;
    @NotNull
    private String size;
    @NotNull @Min(1)
    private Integer numberOfPieces;
    @NotNull @Min(1)
    private Double price;
    @Min(1)
    private Double salePrice;


}
