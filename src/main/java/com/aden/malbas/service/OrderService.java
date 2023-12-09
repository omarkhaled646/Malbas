package com.aden.malbas.service;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.dto.OrderDTO;
import com.aden.malbas.dto.OrderItemDTO;
import com.aden.malbas.model.classes.*;
import com.aden.malbas.model.enums.Status;
import com.aden.malbas.repository.OrderItemRepository;
import com.aden.malbas.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final UserService userService;
    private final ItemService itemService;

    public List<OrderDTO> getOrders(Integer userId) {
        List<Order> orders = orderRepository.findOrdersBy(userId);
        List<OrderDTO> ordersDTO = new ArrayList<>();
        OrderDTO orderDTO;

        for(Order order: orders){
            orderDTO = new OrderDTO(order.getId(), order.getOrderStatus().toString(),
                                    order.getTotalCost());
            ordersDTO.add(orderDTO);
        }

        return ordersDTO;
    }

    public List<OrderItemDTO> getOrderInfo(Integer userId, Integer orderId) {
        List<OrderItemDTO> orderItemsDTO = new ArrayList<>();
        OrderItemDTO orderItemDTO;
        List<OrderItem> orderItems = orderItemRepository.findBy(orderId);

        for(OrderItem orderItem: orderItems){
            orderItemDTO = new OrderItemDTO(orderItem.getItem().getName(), orderItem.getSize(),
                                            orderItem.getNumberOfPieces(), orderItem.getPrice());

            orderItemsDTO.add(orderItemDTO);
        }

        return orderItemsDTO;
    }

    @Transactional
    public void createOrder(Integer userId, List<Integer> itemIds) {
        Double totalCost = 0.0;
        Order order;
        User user;
        List<CartItemDTO> cartItems;

        cartItems = cartService.getItems(userId, itemIds);
        user = userService.getUser(userId);

        for(CartItemDTO cartItem: cartItems){
            if(cartItem.getSalePrice() != null){
                totalCost += cartItem.getSalePrice();
            }
            else{
                totalCost += cartItem.getPrice();
            }
        }

        order = Order
                .builder()
                .user(user)
                .orderStatus(Status.PAID)
                .totalCost(totalCost)
                .build();

        orderRepository.save(order);

        addOrderItems(order, itemIds, cartItems);
        userService.add(order, userId);

    }

    @Transactional
    public void addOrderItems(Order order, List<Integer> itemIds,
                              List<CartItemDTO> cartItems) {
        OrderItem orderItem;
        Integer orderId = order.getId();
        OrderItemKey orderItemKey;
        Item item;

        for(int item_count = 0; item_count < itemIds.size(); item_count++){
            Integer itemId = itemIds.get(item_count);
            CartItemDTO cartItem = cartItems.get(item_count);
            orderItemKey = new OrderItemKey(orderId, itemId);
            item = itemService.getItem(itemId);

            orderItem = OrderItem
                    .builder()
                    .orderItemKey(orderItemKey)
                    .item(item)
                    .order(order)
                    .numberOfPieces(cartItem.getNumberOfPieces())
                    .price(cartItem.getPrice())
                    .size(cartItem.getSize())
                    .build();

            if(cartItem.getSalePrice() != null){
                orderItem.setPrice(cartItem.getSalePrice());
            }

            orderItemRepository.save(orderItem);
        }

    }

    public void updateOrderStatus(Integer orderId, String status) {
        Order order;

        order = orderRepository.findById(orderId).orElse(null);

        // TODO: Add custom exception
        if(order == null){
            throw new RuntimeException();
        }

        order.setOrderStatus(Status.valueOf(status.toUpperCase()));
        orderRepository.save(order);
    }
}
