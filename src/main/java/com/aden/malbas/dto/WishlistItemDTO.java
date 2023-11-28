package com.aden.malbas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistItemDTO {

    @NotNull @NotEmpty @NotBlank
    private String itemName;
    @NotNull @NotEmpty @NotBlank
    private String itemDescription;
    private String itemCollection;
    @NotNull @Min(1)
    private Double price;
    @Min(1)
    private Double salePrice;

    public void setItemDTO(String itemName, String itemDescription,  String itemCollection,
                           Double price){

        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCollection = itemCollection;
        this.price = price;
    }

}
