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

    @GetMapping("wishlist/{wishlistId}")
    public ResponseEntity<List<WishlistItemDTO>> getWishlistItems(@PathVariable Integer wishlistId){
        List<WishlistItemDTO> wishlistItemDTOs = wishlistService.getWishlistItems(wishlistId);
        return new ResponseEntity<>(wishlistItemDTOs, HttpStatus.OK);
    }

    @PostMapping("wishlist/{wishlistId}/addItem")
    public ResponseEntity<String> addItem(@PathVariable Integer wishlistId,
                                          @RequestParam Integer itemId){
        wishlistService.addItem(wishlistId, itemId);
        return new ResponseEntity<>("Item added to the wishlist successfully",
                HttpStatus.CREATED);
    }

    @DeleteMapping("wishlist/{wishlistId}/deleteItem")
    public ResponseEntity<String> deleteItem(@PathVariable Integer wishlistId,
                                             @RequestParam Integer itemId){
        wishlistService.addItem(wishlistId, itemId);
        return new ResponseEntity<>("Item deleted from the wishlist successfully",
                HttpStatus.OK);
    }

}
