package com.aden.malbas.service;

import com.aden.malbas.model.classes.*;
import com.aden.malbas.repository.CartITemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartITemRepository cartITemRepository;
    private final CartService cartService;
    private final ItemService itemService;

    @Transactional
    public void addItem(Integer cartId, Integer itemId, Integer itemCount, String sizeName) {
        Cart cart = cartService.getCart(cartId);
        Item item = itemService.getItem(itemId);
        CartItemKey cartItemKey = new CartItemKey(cartId, itemId);

        // TODO: Add custom exception
        if(!item.isSizeAvailable(sizeName)){
            throw new RuntimeException();
        }

        CartItem cartItem = CartItem
                .builder()
                .cartItemKey(cartItemKey)
                .cart(cart)
                .item(item)
                .itemCount(itemCount)
                .size(sizeName)
                .build();

        cartITemRepository.save(cartItem);
    }
}
