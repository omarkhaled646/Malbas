package com.aden.malbas.service;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.exception.InvalidArgumentException;
import com.aden.malbas.exception.SizeNotAvailableException;
import com.aden.malbas.exception.NotFoundException;
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

        if (cartItemRequest.getNumberOfPieces() == null && cartItemRequest.getSize() == null){
            throw new InvalidArgumentException("You didn't enter the size or the number of pieces or both");
        }

        if (cartItemRequest.getNumberOfPieces() == null){
            throw new InvalidArgumentException("You didn't enter the number of pieces");
        }

        if(!item.isSizeAvailable(cartItemRequest.getSize())){
            throw new SizeNotAvailableException("The requested size is not available for this item");
        }

        if(cart == null){
            throw new NotFoundException("An error occurred! User is Not Found");
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

        if(cartItem == null){
            throw new NotFoundException("This item is not in your cart!");
        }

        if (cartItemRequest.getNumberOfPieces() == null && cartItemRequest.getSize() == null){
            throw new InvalidArgumentException("You didn't edit the requested item");
        }else {
            if (cartItemRequest.getNumberOfPieces() != null) {
                cartItem.setNumberOfPieces(cartItemRequest.getNumberOfPieces());
            }
            if (cartItemRequest.getSize() != null) {

                if (!cartItem.getItem().isSizeAvailable(cartItemRequest.getSize())) {
                    throw new SizeNotAvailableException("The requested size is not available for this item");
                }
                cartItem.setSize(cartItem.getSize());
            }
        }

        cartITemRepository.save(cartItem);
    }

    @Transactional
    public void deleteItem(Integer userId, Integer itemId) {
        Integer cartId = userService.getUser(userId).getCart().getId();
        cartItemKey = new CartItemKey(cartId, itemId);
        try {
            cartITemRepository.deleteById(cartItemKey);
        }catch (Exception exception){
            throw new NotFoundException("The requested item is not found in your cart");
        }
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

        if(cartItem == null){
            throw new NotFoundException("This item is not in your cart!");
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
