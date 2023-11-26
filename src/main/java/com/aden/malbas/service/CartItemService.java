package com.aden.malbas.service;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.repository.CartITemRepository;
import com.aden.malbas.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartITemRepository cartITemRepository;
    private final ItemRepository itemRepository;
    private final CartService cartService;
    private final ItemService itemService;

    private CartItemKey cartItemKey;

    @Transactional
    public void addItem(Integer cartId, Integer itemId, Integer numberOfPieces, String size){
        Cart cart = cartService.getCart(cartId);
        Item item = itemService.getItem(itemId);
        cartItemKey = new CartItemKey(cartId, itemId);

        // TODO: Add custom exception
        if(!item.isSizeAvailable(size)){
            throw new RuntimeException();
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

    public void updateItem(Integer cartId, Integer itemId, Integer numberOfPieces, String size) {
        cartItemKey = new CartItemKey(cartId, itemId);
        CartItem cartItemObject = cartITemRepository.findById(cartItemKey).orElse(null);

        // TODO: Add custom exception
        if(cartItemObject == null){
            throw new NullPointerException();
        }

        if(numberOfPieces != null){
            cartItemObject.setNumberOfPieces(numberOfPieces);
        }

        if(size != null){

            if(!cartItemObject.getItem().isSizeAvailable(size)){
                throw new RuntimeException();
            }
            cartItemObject.setSize(size);
        }

        cartITemRepository.save(cartItemObject);
    }

    public void deleteItem(Integer cartId, Integer itemId) {
        cartItemKey = new CartItemKey(cartId, itemId);

        cartITemRepository.deleteById(cartItemKey);
    }

    public List<CartItemDTO> getCartItems(Integer cartId) {
        List<Integer> itemIds = cartITemRepository.findCartItems(cartId);
        List<CartItemDTO> cartItems = new ArrayList<>();
        CartItemDTO cartItemDTO;
        Item item;
        CartItem cartItem;

        for(Integer id: itemIds){
            item = itemRepository.findById(id).orElse(null);

            //TODO: Add custom exception
            if(item == null){
                throw new NullPointerException();
            }

            cartItemKey = new CartItemKey(cartId, id);
            cartItem = cartITemRepository.findById(cartItemKey).orElse(null);

            //TODO: Add custom exception
            if(cartItem == null){
                throw new NullPointerException();
            }


            cartItemDTO = new CartItemDTO();
            cartItemDTO.setItemDTO(item.getName(), item.getDescription(),
                        item.getPrice(), cartItem.getSize(),
                        cartItem.getNumberOfPieces());

            if(item.getSalePrice() != null){
                cartItemDTO.setSalePrice(item.getSalePrice());
            }

            cartItems.add(cartItemDTO);
        }

        return cartItems;
    }
}
