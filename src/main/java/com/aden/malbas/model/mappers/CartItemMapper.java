package com.aden.malbas.model.mappers;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.model.classes.CartItem;
import com.aden.malbas.model.classes.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mappings({
            @Mapping(source = "item.name", target = "itemName"),
            @Mapping(source = "item.description", target = "itemDescription"),
            @Mapping(source = "item.price", target = "price"),
            @Mapping(source = "item.salePrice", target = "salePrice", defaultValue = "0.0"),
            @Mapping(source = "cartItem.size", target = "size"),
            @Mapping(source = "cartItem.numberOfPieces", target = "numberOfPieces")
    })
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem, Item item);

}
