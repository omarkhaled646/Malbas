package com.aden.malbas.service;

import com.aden.malbas.model.classes.Cart;
import com.aden.malbas.model.classes.CartItem;
import com.aden.malbas.model.classes.Item;
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
    public void addItem(Integer cartId, Integer itemId, Integer itemCount) {
        Cart cart = cartService.getCart(cartId);
        Item item = itemService.getItem(itemId);
        CartItem cartItem = CartItem
                .builder()
                .cart(cart)
                .item(item)
                .itemCount(itemCount)
                .build();

        cartITemRepository.save(cartItem);
    }
}
