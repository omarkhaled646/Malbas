package com.aden.malbas.controller;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.request.CartItemRequest;
import com.aden.malbas.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("cart/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Integer userId){
        List<CartItemDTO> cartItemDTOs = cartService.getCartItems(userId);
        return new ResponseEntity<>(cartItemDTOs, HttpStatus.OK);
    }

    @PostMapping("cart/{userId}/addItem")
    public ResponseEntity<String> addItem(@PathVariable Integer userId,
                                          @RequestBody CartItemRequest cartItemRequest){
        cartService.addItem(userId, cartItemRequest);
        return new ResponseEntity<>("Item added to the cart successfully",
                HttpStatus.CREATED);
    }

    @PutMapping("cart/{userId}/updateItem")
    public ResponseEntity<String> updateItem(@PathVariable Integer userId,
                                                     @RequestBody CartItemRequest cartItemRequest){
        cartService.updateItem(userId, cartItemRequest);
        return new ResponseEntity<>("Item updated in the cart successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("cart/{userId}/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer userId, @PathVariable Integer itemId){
        cartService.deleteItem(userId, itemId);
        return new ResponseEntity<>("Item deleted from the cart successfully",
                HttpStatus.OK);
    }

    @PutMapping("cart/{userId}/moveItemFromCartToWishlist/{itemId}")
    public ResponseEntity<String> moveItemFromCartToWishlist(@PathVariable Integer userId,
                                           @PathVariable Integer itemId){
       cartService.moveItemFromCartToWishlist(userId,itemId);
       return new ResponseEntity<>("Item added to wishlist from the cart successfully",
               HttpStatus.OK);
    }

}
