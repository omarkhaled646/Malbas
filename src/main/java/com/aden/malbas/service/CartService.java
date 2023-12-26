package com.aden.malbas.service;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.model.mappers.CartItemMapper;
import com.aden.malbas.repository.CartITemRepository;
import com.aden.malbas.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartITemRepository cartITemRepository;
    private final CartRepository cartRepository;
    private final ItemService itemService;
    private final WishlistService wishlistService;
    @Autowired
    private CartItemMapper cartItemMapper;
    private CartItemKey cartItemKey;

    @Transactional
    public void addItem(Integer cartId, Integer itemId, Integer numberOfPieces, String size){
        Item item = itemService.getItem(itemId);
        Cart cart = cartRepository.findById(cartId).orElse(null);
        cartItemKey = new CartItemKey(cartId, itemId);

        // TODO: Add custom exception
        if(!item.isSizeAvailable(size)){
            throw new RuntimeException();
        }

        // TODO: Add custom exception
        if(cart == null){
            throw new NullPointerException();
        }

        CartItem cartItem = CartItem
                .builder()
                .cartItemKey(cartItemKey)
                .cart(cart)
                .item(item)
                .numberOfPieces(numberOfPieces)
                .size(size)
                .build();

        cartITemRepository.save(cartItem);
    }

    @Transactional
    public void updateItem(Integer cartId, Integer itemId, Integer numberOfPieces, String size) {
        cartItemKey = new CartItemKey(cartId, itemId);
        CartItem cartItem = cartITemRepository.findById(cartItemKey).orElse(null);

        // TODO: Add custom exception
        if(cartItem == null){
            throw new NullPointerException();
        }

        if(numberOfPieces != null){
            cartItem.setNumberOfPieces(numberOfPieces);
        }

        if(size != null){

            if(!cartItem.getItem().isSizeAvailable(size)){
                throw new RuntimeException();
            }
            cartItem.setSize(size);
        }

        cartITemRepository.save(cartItem);
    }

    @Transactional
    public void deleteItem(Integer cartId, Integer itemId) {
        cartItemKey = new CartItemKey(cartId, itemId);

        cartITemRepository.deleteById(cartItemKey);
    }

    public List<CartItemDTO> getCartItems(Integer cartId) {
        List<Integer> itemIds = cartITemRepository.findCartItems(cartId);
        List<CartItemDTO> cartItems = new ArrayList<>();
        CartItemDTO cartItemDTO;

        for(Integer id: itemIds){
            cartItemDTO = generateCartItemDTO(id, cartId);
            cartItems.add(cartItemDTO);
        }

        return cartItems;
    }

    public List<CartItemDTO> getItems(Integer cartId, List<Integer> itemIds) {
        List<CartItemDTO> cartItems = new ArrayList<>();
        CartItemDTO cartItemDTO;

        for(Integer id: itemIds){
            cartItemDTO = generateCartItemDTO(id, cartId);
            cartItems.add(cartItemDTO);
        }

        return cartItems;
    }

    public CartItemDTO generateCartItemDTO(Integer itemId, Integer cartId){
        CartItemDTO cartItemDTO;
        Item item;
        CartItem cartItem;

        item = itemService.getItem(itemId);
        cartItemKey = new CartItemKey(cartId, itemId);
        cartItem = cartITemRepository.findById(cartItemKey).orElse(null);

        //TODO: Add custom exception
        if(cartItem == null){
            throw new NullPointerException();
        }

        cartItemDTO = cartItemMapper.cartItemToCartItemDTO(cartItem, item);


      return cartItemDTO;
    }

    public void deleteItems(Integer cartId, List<Integer> itemIds) {

        for(Integer id: itemIds){
            this.deleteItem(cartId, id);
        }
    }

    public void moveItemFromCartToWishlist(Integer cartId, Integer wishlistId, Integer itemId) {
        CartItemDTO cartItemDTO = generateCartItemDTO(itemId, cartId);
        this.deleteItem(cartId, itemId);
        wishlistService.addItem(wishlistId, itemId);
    }
}
