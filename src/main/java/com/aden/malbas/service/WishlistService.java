package com.aden.malbas.service;

import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.model.classes.Item;
import com.aden.malbas.model.classes.Wishlist;
import com.aden.malbas.model.mappers.WishlistItemMapper;
import com.aden.malbas.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemService itemService;
    private final UserService userService;
    @Autowired
    private final WishlistItemMapper wishlistItemMapper;

    public List<WishlistItemDTO> getWishlistItems(Integer userId) {
        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);

        // TODO: Add custom exception
        if(wishlist == null){
            throw new NullPointerException();
        }

        List<Item> items = wishlist.getItems();
        List<WishlistItemDTO> wishlistItems = new ArrayList<>();
        WishlistItemDTO wishlistItemDTO;

        for(Item item: items){
            wishlistItemDTO = wishlistItemMapper.itemToWishlistItemDTO(item);
            wishlistItems.add(wishlistItemDTO);
        }

        return wishlistItems;
    }

    @Transactional
    public void addItem(Integer userId, Integer itemId) {
        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        Item item = itemService.getItem(itemId);
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

    @Transactional
    public void deleteItem(Integer userId, Integer itemId) {
        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        Item item = itemService.getItem(itemId);
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

}
