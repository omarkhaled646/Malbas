package com.aden.malbas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistItemDTO {

    private String itemName;
    private String itemDescription;
    private String itemCollection;
    private String size;
    private Double price;
    private Double salePrice;

    public void setItemDTO(String itemName, String itemDescription,  String itemCollection,
                           Double price){

        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCollection = itemCollection;
        this.price = price;
    }

    public void setItemDTO(String itemName, String itemDescription,  String itemCollection,
                           Double price, String size){

        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.size = size;
        this.itemCollection = itemCollection;
        this.price = price;
    }

}
