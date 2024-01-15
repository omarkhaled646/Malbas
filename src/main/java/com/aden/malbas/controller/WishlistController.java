package com.aden.malbas.controller;

import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("wishlist/{userId}")
    public ResponseEntity<List<WishlistItemDTO>> getWishlistItems(@PathVariable Integer userId){
        List<WishlistItemDTO> wishlistItemDTOs = wishlistService.getWishlistItems(userId);
        return new ResponseEntity<>(wishlistItemDTOs, HttpStatus.OK);
    }

    @PostMapping("wishlist/{userId}/addItem/{itemId}")
    public ResponseEntity<String> addItem(@PathVariable Integer userId,
                                          @PathVariable Integer itemId){
        wishlistService.addItem(userId, itemId);
        return new ResponseEntity<>("Item added to the wishlist successfully",
                HttpStatus.CREATED);
    }

    @DeleteMapping("wishlist/{userId}/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer userId,
                                             @PathVariable Integer itemId){
        wishlistService.deleteItem(userId, itemId);
        return new ResponseEntity<>("Item deleted from the wishlist successfully",
                HttpStatus.OK);
    }

}
