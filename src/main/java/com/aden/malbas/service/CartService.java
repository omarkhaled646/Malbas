package com.aden.malbas.service;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.model.mappers.CartItemMapper;
import com.aden.malbas.repository.CartITemRepository;
import com.aden.malbas.repository.CartRepository;
import com.aden.malbas.request.CartItemRequest;
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
    private final UserService userService;
    private final ItemService itemService;
    private final WishlistService wishlistService;
    @Autowired
    private CartItemMapper cartItemMapper;
    private CartItemKey cartItemKey;

    @Transactional
    public void addItem(Integer userId, CartItemRequest cartItemRequest){
        Item item = itemService.getItem(cartItemRequest.getItemId());
        Integer cartId = userService.getUser(userId).getCart().getId();
        Cart cart = cartRepository.findById(cartId).orElse(null);
        cartItemKey = new CartItemKey(cartId, cartItemRequest.getItemId());

        // TODO: Add custom exception
        if(!item.isSizeAvailable(cartItemRequest.getSize())){
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
                .numberOfPieces(cartItemRequest.getNumberOfPieces())
                .size(cartItemRequest.getSize())
                .build();

        cartITemRepository.save(cartItem);
    }

    @Transactional
    public void updateItem(Integer userId, CartItemRequest cartItemRequest) {
        Integer cartId = userService.getUser(userId).getCart().getId();
        cartItemKey = new CartItemKey(cartId, cartItemRequest.getItemId());
        CartItem cartItem = cartITemRepository.findById(cartItemKey).orElse(null);

        // TODO: Add custom exception
        if(cartItem == null){
            throw new NullPointerException();
        }

        if(cartItemRequest.getNumberOfPieces() != null){
            cartItem.setNumberOfPieces(cartItemRequest.getNumberOfPieces());
        }

        if(cartItemRequest.getSize() != null){

            if(!cartItem.getItem().isSizeAvailable(cartItemRequest.getSize())){
                throw new RuntimeException();
            }
            cartItem.setSize(cartItem.getSize());
        }

        cartITemRepository.save(cartItem);
    }

    @Transactional
    public void deleteItem(Integer userId, Integer itemId) {
        Integer cartId = userService.getUser(userId).getCart().getId();
        System.out.println(cartId);
        cartItemKey = new CartItemKey(cartId, itemId);

        cartITemRepository.deleteById(cartItemKey);
    }

    public List<CartItemDTO> getCartItems(Integer userId) {
        Integer cartId = userService.getUser(userId).getCart().getId();
        List<Integer> itemIds = cartITemRepository.findCartItems(cartId);
        List<CartItemDTO> cartItems = new ArrayList<>();
        CartItemDTO cartItemDTO;

        for(Integer id: itemIds){
            cartItemDTO = generateCartItemDTO(id, cartId);
            cartItems.add(cartItemDTO);
        }

        return cartItems;
    }

    public List<CartItemDTO> getItems(Integer userId, List<Integer> itemIds) {
        Integer cartId = userService.getUser(userId).getCart().getId();
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

    public void deleteItems(Integer userId, List<Integer> itemIds) {
        Integer cartId = userService.getUser(userId).getCart().getId();
        for(Integer id: itemIds){
            this.deleteItem(cartId, id);
        }
    }

    public void moveItemFromCartToWishlist(Integer userId, Integer itemId) {
        Integer cartId = userService.getUser(userId).getCart().getId();
        this.deleteItem(cartId, itemId);
        wishlistService.addItem(userId, itemId);
    }
}
