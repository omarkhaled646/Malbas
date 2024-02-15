package com.aden.malbas.controller;

import com.aden.malbas.dto.OrderDTO;
import com.aden.malbas.dto.OrderItemDTO;
import com.aden.malbas.exception.*;
import com.aden.malbas.request.OrderCreationRequest;
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
        try {
            List<OrderDTO> orderDTOs = orderService.getOrders(userId);
            return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
        }catch (NotFoundException userNotFoundException){
            throw new NotFoundException(userNotFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @GetMapping("{userId}/orders/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getOrderInfo(@PathVariable Integer userId,
                                                           @PathVariable Integer orderId){
        try {
            List<OrderItemDTO> orderItemDTOs = orderService.getOrderInfo(userId, orderId);
            return new ResponseEntity<>(orderItemDTOs, HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PostMapping("{userId}/createOrder")
    public ResponseEntity<String> createOrder(@PathVariable Integer userId,
                                              @RequestBody OrderCreationRequest orderCreationRequest){
        try {
            orderService.createOrder(userId, orderCreationRequest);
            return new ResponseEntity<>("Order created successfully.", HttpStatus.CREATED);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PutMapping("admin/updateOrderStatus/{orderId}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Integer orderId,
                                              @RequestParam String status){
        try {
            orderService.updateOrderStatus(orderId, status);
            return new ResponseEntity<>("Order updated successfully.", HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch(InvalidArgumentException invalidArgumentException){
            throw new InvalidArgumentException(invalidArgumentException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<String> invalidArgumentException(InvalidArgumentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> serverExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
