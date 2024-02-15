package com.aden.malbas.service;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.exception.InvalidArgumentException;
import com.aden.malbas.exception.NotFoundException;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.model.mappers.ItemMapper;
import com.aden.malbas.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    @Autowired
    private ItemMapper itemMapper;

    public Item getItem(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NotFoundException("The requested item is unavailable");
        }

        return item;
    }

    @Transactional
    public void add(ItemDTO itemDTO) {

        if(itemDTO.getCategory() == null){
            throw new InvalidArgumentException("You didn't provide a category for the item!");
        }
        Item newItem =  createItemByCategory(itemDTO, itemDTO.getCategory());
        if (newItem == null){
            throw new NotFoundException("The requested category is not found");
        }

        itemRepository.save(newItem);

    }

    private Item createItemByCategory(ItemDTO itemDTO, String category) {

        Item newItem;
        switch (category.toLowerCase()){
            case "men":
                newItem = itemMapper.menItemDTOToItem(itemDTO);
                break;
            case "women":
                newItem = itemMapper.womenItemDTOToItem(itemDTO);
                break;
            case "kids":
                newItem = itemMapper.kidsItemDTOToItem(itemDTO);
                break;
            case "home":
                newItem = itemMapper.homeItemDTOToItem(itemDTO);
                break;
            case "personal":
                newItem = itemMapper.personalItemDTOToItem(itemDTO);
                break;
            default:
                newItem = null;

        }

        return newItem;
    }

    @Transactional
    public void deleteItem(Integer itemId) {
        try {
            itemRepository.deleteById(itemId);
        }catch (Exception exception){
            throw new NotFoundException("The requested item is not found");
        }
    }

    public List<ItemDTO> findBy(String category) {
        List<Item> items;
        try {
            items = itemRepository.findByCategory(category);
        }catch (Exception exception){
            throw new NotFoundException("The requested category is not found");
        }
        List<ItemDTO> itemsDTO = new ArrayList<>();

        for (Item item: items){
            itemsDTO.add(itemMapper.itemToItemDTO(item));
        }
        return itemsDTO;
    }

    @Transactional
    public void addColor(Integer itemId, String colorName) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NotFoundException("The requested item is not found");
        }

        if (colorName == null){
            throw new InvalidArgumentException("You didn't provide a color name!");
        }
        item.addColor(colorName);
        itemRepository.save(item);
    }

    @Transactional
    public void addSize(Integer itemId, String sizeName) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NotFoundException("The requested item is not found");
        }

        item.addSize(sizeName);
        itemRepository.save(item);
    }

    @Transactional
    public void addSale(Integer itemId, Double salePrice) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NotFoundException("The requested item is not found");
        }

        if (salePrice == null){
            throw new InvalidArgumentException("You didn't provide a sale price!");
        }

        if (salePrice <= 0){
            throw new InvalidArgumentException("You entered wrong price!");
        }

        item.setSalePrice(salePrice);
        itemRepository.save(item);
    }

    @Transactional
    public void removeSale(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NotFoundException("The requested item is not found");
        }


        item.setSalePrice(null);
        itemRepository.save(item);
    }

    @Transactional
    public void addPieces(Integer itemId, Integer piecesCount) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null){
            throw new NotFoundException("The requested item is not found");
        }

        if (piecesCount == null){
            throw new InvalidArgumentException("You didn't provide a number of pieces!");
        }
        if (piecesCount <= 0){
            throw new InvalidArgumentException("You entered wrong number!");
        }
        item.addPieces(item, piecesCount);
        itemRepository.save(item);
    }
}
