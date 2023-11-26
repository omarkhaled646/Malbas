package com.aden.malbas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemDTO {

    private String itemName;
    private String itemDescription;
    private String size;
    private Integer numberOfPieces;
    private Double price;
    private Double salePrice;


    public void setItemDTO(String itemName, String itemDescription, Double price,
                           String size, Integer numberOfPieces){

        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.size = size;
        this.numberOfPieces = numberOfPieces;
        this.price = price;
    }

}
