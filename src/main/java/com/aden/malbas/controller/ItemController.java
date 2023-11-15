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
    public void addItem(ItemDTO item, String category, List<String> availableSizes) {
        itemService.addItem(item, category, availableSizes);
    }
}
