package com.aden.malbas.model.mappers;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.classes.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    MenItem menItemDTOToItem(ItemDTO itemDTO);
    WomenItem womenItemDTOToItem(ItemDTO itemDTO);
    KidsItem kidsItemDTOToItem(ItemDTO itemDTO);
    HomeItem homeItemDTOToItem(ItemDTO itemDTO);
    PersonalItem personalItemDTOToItem(ItemDTO itemDTO);

    ItemDTO itemToItemDTO(Item item);

    @AfterMapping
    default void mapAvailableSizes(ItemDTO itemDTO, @MappingTarget Item item){
        item.addAvailableSizes(itemDTO.getAvailableSizes());
    }
}
