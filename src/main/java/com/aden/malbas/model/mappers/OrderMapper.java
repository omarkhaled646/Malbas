package com.aden.malbas.model.mappers;

import com.aden.malbas.dto.OrderDTO;
import com.aden.malbas.model.classes.Order;
import com.aden.malbas.model.enums.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    default Status mapRole(String status){
        return Status.valueOf(status.toUpperCase());
    }

    Order orderDTOToOrder(OrderDTO orderDTO);

    OrderDTO orderToOrderDTO(Order order);
}
