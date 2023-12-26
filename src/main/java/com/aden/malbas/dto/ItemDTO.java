package com.aden.malbas.dto;

import com.aden.malbas.model.classes.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDTO {
    @NotNull
    private Integer id;
    @NotNull @NotEmpty @NotBlank
    private String name;
    private String collection;
    @NotNull
    private String description;
    @NotNull @Min(1)
    private Double price;
    @NotNull @Min(1)
    private Integer availablePiecesCount;
    @NotNull @NotEmpty
    private List<String> availableColors;
    @NotNull @NotEmpty
    private List<String> availableSizes;
    @Min(1)
    private Double salePrice;


}