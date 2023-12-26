package com.aden.malbas.model.mappers;

import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.model.classes.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WishlistItemMapper {

    @Mappings({
            @Mapping(source = "item.name", target = "itemName"),
            @Mapping(source = "item.description", target = "itemDescription"),
            @Mapping(source = "item.collection", target = "itemCollection"),
            @Mapping(source = "item.price", target = "price"),
            @Mapping(source = "item.salePrice", target = "salePrice", defaultValue = "0.0")
    })
    WishlistItemDTO itemToWishlistItemDTO(Item item);
}
