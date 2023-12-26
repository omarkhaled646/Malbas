package com.aden.malbas.controller;

import com.aden.malbas.dto.CartItemDTO;
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

    @GetMapping("cart/{cartId}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Integer cartId){
        List<CartItemDTO> cartItemDTOs = cartService.getCartItems(cartId);
        return new ResponseEntity<>(cartItemDTOs, HttpStatus.OK);
    }

    @PostMapping("cart/{cartId}/addItem")
    public ResponseEntity<String> addItem(@PathVariable Integer cartId,
                                                   @RequestParam Integer itemId,
                                                   @RequestParam  Integer numberOfPieces,
                                                   @RequestParam String size){
        cartService.addItem(cartId, itemId, numberOfPieces, size);
        return new ResponseEntity<>("Item added to the cart successfully",
                HttpStatus.CREATED);
    }

    @PutMapping("cart/{cartId}/updateItem")
    public ResponseEntity<String> updateItem(@PathVariable Integer cartId,
                                                      @RequestParam Integer itemId,
                                                      @RequestParam(required = false)
                                                      Integer numberOfPieces,
                                                      @RequestParam(required = false)
                                                      String size){
        cartService.updateItem(cartId, itemId, numberOfPieces, size);
        return new ResponseEntity<>("Item updated in the cart successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("cart/{cartId}/deleteItem")
    public ResponseEntity<String> deleteItem(@PathVariable Integer cartId, @RequestParam Integer itemId){
        cartService.deleteItem(cartId, itemId);
        return new ResponseEntity<>("Item deleted from the cart successfully",
                HttpStatus.OK);
    }

    @PutMapping("cart/{cartId}/moveItemFromCartToWishlist/{wishlistId}")
    public ResponseEntity<String> moveItemFromCartToWishlist(@PathVariable Integer cartId,
                                           @PathVariable Integer wishlistId,
                                           @RequestParam Integer itemId){
       cartService.moveItemFromCartToWishlist(cartId,wishlistId,itemId);
       return new ResponseEntity<>("Item added to wishlist from the cart successfully",
               HttpStatus.OK);
    }


    public List<CartItemDTO> getItemsForOrder(Integer cartId, List<Integer> itemIds) {
        return cartService.getItems(cartId, itemIds);
    }

    public void deleteItems(Integer cartId, List<Integer> itemIds) {
        cartService.deleteItems(cartId, itemIds);
    }
}
