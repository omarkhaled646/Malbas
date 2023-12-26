package com.aden.malbas.model.mappers;

import com.aden.malbas.dto.OrderItemDTO;
import com.aden.malbas.model.classes.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mappings({
            @Mapping(source = "orderItem.item.name", target = "itemName"),
    })
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
}
