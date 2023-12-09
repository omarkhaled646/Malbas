package com.aden.malbas.controller;

import com.aden.malbas.dto.OrderDTO;
import com.aden.malbas.dto.OrderItemDTO;
import com.aden.malbas.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("{userId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrders(@PathVariable Integer userId){
        List<OrderDTO> ordersDTO = orderService.getOrders(userId);
        return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
    }

    @GetMapping("{userId}/orders/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getOrderInfo(@PathVariable Integer userId,
                                                           @PathVariable Integer orderId){
        List<OrderItemDTO> orderItemsDTO = orderService.getOrderInfo(userId, orderId);
        return new ResponseEntity<>(orderItemsDTO, HttpStatus.OK);
    }

    @PostMapping("{userId}/createOrder")
    public ResponseEntity<String> createOrder(@PathVariable Integer userId,
                                              @RequestParam List<Integer> itemIds){
        orderService.createOrder(userId, itemIds);
        return new ResponseEntity<>("Order created successfully.", HttpStatus.CREATED);
    }

    @PutMapping("admin/updateOrderStatus/{orderId}")
    public ResponseEntity<String> createOrder(@PathVariable Integer orderId,
                                              @RequestParam String status){
        orderService.updateOrderStatus(orderId, status);
        return new ResponseEntity<>("Order updated successfully.", HttpStatus.OK);
    }

}
