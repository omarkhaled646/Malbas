package com.aden.malbas.service;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public void add(ItemDTO itemDTO, String category, List<String> availableSizes) {

        if(category == null || availableSizes == null){
            throw new NullPointerException();
        }
        Item newItem =  createItemByCategory(itemDTO, category, availableSizes);
        if (newItem == null){
            throw new NullPointerException();
        }

        itemRepository.save(newItem);

    }

    private Item createItemByCategory(ItemDTO itemDTO, String category,
                                      List<String> availableSizes) {

        Item newItem = null;
        switch (category.toLowerCase()){
            case "men":
                newItem = MenItem.createMenItem(itemDTO, availableSizes);
                break;
            case "women":
                newItem = WomenItem.createWomenItem(itemDTO, availableSizes);
                break;
            case "kids":
                newItem = KidsItem.createKidsItem(itemDTO, availableSizes);
                break;
            case "home":
                newItem = HomeItem.createHomeItem(itemDTO, availableSizes);
                break;
            case "personal":
                newItem = PersonalItem.createPersonalItem(itemDTO, availableSizes);
            default:
                newItem = null;

        }

        return newItem;
    }

    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }

    public List<ItemDTO> findBy(String category) {
        List<Item> items = itemRepository.findByCategory(category);
        List<ItemDTO> itemsDTO = new ArrayList<>();

        for (Item item: items){
            itemsDTO.add(new ItemDTO(item));
        }
        return itemsDTO;
    }

    public void addColor(Integer itemId, String colorName) {
        Item item = itemRepository.findById(itemId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        item.addColor(colorName);
        itemRepository.save(item);
    }

    public void addSize(Integer itemId, String sizeName) {
        Item item = itemRepository.findById(itemId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        item.addSize(sizeName);
        itemRepository.save(item);
    }

    public void addSale(Integer itemId, Double salePrice) {
        Item item = itemRepository.findById(itemId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        item.setSalePrice(salePrice);
        itemRepository.save(item);
    }

    public void removeSale(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        item.setSalePrice(null);
        itemRepository.save(item);
    }

    public void addPieces(Integer itemId, Integer piecesCount) {
        Item item = itemRepository.findById(itemId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        item.addPieces(item, piecesCount);
        itemRepository.save(item);
    }
}
