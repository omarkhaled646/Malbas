package com.aden.malbas.service;

import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.model.classes.Item;
import com.aden.malbas.model.classes.Wishlist;
import com.aden.malbas.repository.ItemRepository;
import com.aden.malbas.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;

    public void addItemToTheWishlist(Integer wishlistId, Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        // TODO: Add custom exception
        if(wishlist == null){
            throw new NullPointerException();
        }

        wishlist.add(item);

        wishlistRepository.save(wishlist);
    }

    public void deleteItemFromTheWishlist(Integer wishlistId, Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);

        // TODO: Add custom exception
        if(item == null){
            throw new NullPointerException();
        }

        // TODO: Add custom exception
        if(wishlist == null){
            throw new NullPointerException();
        }

        wishlist.delete(item);

        wishlistRepository.save(wishlist);
    }

    public List<WishlistItemDTO> getWishlistItems(Integer wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);

        // TODO: Add custom exception
        if(wishlist == null){
            throw new NullPointerException();
        }

        List<Item> items = wishlist.getItems();
        List<WishlistItemDTO> wishlistItems = new ArrayList<>();
        WishlistItemDTO wishlistItemDTO;

        for(Item item: items){
            wishlistItemDTO = new WishlistItemDTO();
            wishlistItemDTO.setItemDTO(item.getName(), item.getDescription(),
                    item.getCollection(), item.getPrice());

            if(item.getSalePrice() != null){
                wishlistItemDTO.setSalePrice(item.getSalePrice());
            }

            wishlistItems.add(wishlistItemDTO);
        }

        return wishlistItems;
    }
}
