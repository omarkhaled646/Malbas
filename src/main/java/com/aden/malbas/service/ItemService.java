package com.aden.malbas.service;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item getItem(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NullPointerException();
        }

        return item;
    }

    @Transactional
    public void addItem(ItemDTO item, String category, List<String> availableSizes) {

        if(category == null || availableSizes == null){
            throw new NullPointerException();
        }
        Item newItem =  createItemByCategory(item, category, availableSizes);
        if (newItem == null){
            throw new NullPointerException();
        }

        itemRepository.save(newItem);

    }

    private Item createItemByCategory(ItemDTO item, String category,
                                      List<String> availableSizes) {

        Item newItem = null;
        switch (category.toLowerCase()){
            case "men":
                newItem = MenItem.createMenItem(item, availableSizes);
                break;
            case "women":
                newItem = WomenItem.createWomenItem(item, availableSizes);
                break;
            case "kids":
                newItem = KidsItem.createKidsItem(item, availableSizes);
                break;
            case "home":
                newItem = HomeItem.createHomeItem(item, availableSizes);
                break;
            case "personal":
                newItem = PersonalItem.createPersonalItem(item, availableSizes);
            default:
                newItem = null;

        }

        return newItem;
    }

}
