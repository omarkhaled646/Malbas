package com.aden.malbas.service;

import com.aden.malbas.model.classes.Cart;
import com.aden.malbas.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public Cart getCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);

        if(cart == null){
            throw new NullPointerException();
        }

        return cart;
    }
}
