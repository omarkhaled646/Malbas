package com.aden.malbas.controller;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    public void add(ItemDTO item, String category, List<String> availableSizes) {
        itemService.add(item, category, availableSizes);
    }

    public void deleteItem(Integer itemId) {
        itemService.deleteItem(itemId);
    }

    public List<ItemDTO> findBy(String category) {
        return itemService.findBy(category);
    }


    public void addColor(Integer itemId, String colorName) {
        itemService.addColor(itemId, colorName);
    }

    public void addSize(Integer itemId, String sizeName) {
        itemService.addSize(itemId, sizeName);
    }

    public void addSale(Integer itemId, Double salePrice) {
        itemService.addSale(itemId, salePrice);
    }

    public void removeSale(Integer itemId) {
        itemService.removeSale(itemId);
    }

    public void addPieces(Integer itemId, Integer piecesCount) {
        itemService.addPieces(itemId, piecesCount);
    }
}
