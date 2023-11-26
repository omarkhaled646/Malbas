package com.aden.malbas.controller;

import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    public void addItemToTheWishlist(Integer wishlistId, Integer itemId) {
        wishlistService.addItemToTheWishlist(wishlistId, itemId);
    }

    public void deleteItemFromTheWishList(Integer wishlistId, Integer itemId) {
        wishlistService.deleteItemFromTheWishlist(wishlistId, itemId);
    }

    public List<WishlistItemDTO> getWishlistItems(Integer wishlistId) {
        List<WishlistItemDTO> wishlistItems = wishlistService.getWishlistItems(wishlistId);

        return wishlistItems;
    }
}
