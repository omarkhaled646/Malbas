package com.aden.malbas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    @NotNull @NotEmpty @NotBlank
    private String itemName;
    @NotNull
    private String size;
    @NotNull @Min(1)
    private Integer numberOfPieces;
    @NotNull @Min(1)
    private Double price;

}
