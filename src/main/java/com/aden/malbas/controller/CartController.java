package com.aden.malbas.controller;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    public void addItemToTheCart(Integer cartId, Integer itemId, Integer numberOfPieces,
                                 String size) {
        cartItemService.addItem(cartId, itemId, numberOfPieces, size);
    }

    public void updateItemInTheCart(Integer cartId, Integer itemId, Integer numberOfPieces,
                                    String size) {
        cartItemService.updateItem(cartId, itemId, numberOfPieces, size);
    }

    public void deleteItemFromTheCart(Integer cartId, Integer itemId) {
        cartItemService.deleteItem(cartId, itemId);
    }

    public List<CartItemDTO> getCartItem(Integer cartId) {
        List<CartItemDTO> cartItems = cartItemService.getCartItems(cartId);

        return cartItems;
    }

}
