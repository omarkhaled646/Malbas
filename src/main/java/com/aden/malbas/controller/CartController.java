package com.aden.malbas.controller;

import com.aden.malbas.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    public void addItemToTheCart(Integer cartId, Integer itemId, Integer itemCount) {
        cartItemService.addItem(cartId, itemId, itemCount);
    }
}
