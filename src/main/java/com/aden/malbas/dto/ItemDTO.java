package com.aden.malbas.dto;

import com.aden.malbas.model.classes.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDTO {
    private Integer id;
    private String name;
    private String collection;
    private String description;
    private Double price;
    private Integer availablePiecesCount;
    private List<String> availableColors;
    private Double salePrice;

    public ItemDTO(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.collection = item.getCollection();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.availablePiecesCount = item.getAvailablePiecesCount();
        this.availableColors = item.getAvailableColors();
        this.salePrice = item.getSalePrice();
    }

}