package com.aden.malbas.controller;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.exception.*;
import com.aden.malbas.request.CartItemRequest;
import com.aden.malbas.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("cart/{userId}")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Integer userId){
        try{
            List<CartItemDTO> cartItemDTOs = cartService.getCartItems(userId);
            return new ResponseEntity<>(cartItemDTOs, HttpStatus.OK);
        }catch (NotFoundException userNotFoundException){
            throw new NotFoundException(userNotFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }

    }

    @PostMapping("cart/{userId}/addItem")
    public ResponseEntity<String> addItem(@PathVariable Integer userId,
                                          @RequestBody CartItemRequest cartItemRequest){

        try {
            cartService.addItem(userId, cartItemRequest);
            return new ResponseEntity<>("Item added to the cart successfully",
                    HttpStatus.CREATED);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch(InvalidArgumentException invalidArgumentException){
            throw new InvalidArgumentException(invalidArgumentException.getMessage());
        }catch (SizeNotAvailableException sizeNotAvailableException){
            throw new SizeNotAvailableException(sizeNotAvailableException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PutMapping("cart/{userId}/updateItem")
    public ResponseEntity<String> updateItem(@PathVariable Integer userId,
                                                     @RequestBody CartItemRequest cartItemRequest){
        try{
            cartService.updateItem(userId, cartItemRequest);
            return new ResponseEntity<>("Item updated in the cart successfully",
                HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch(InvalidArgumentException invalidArgumentException){
            throw new InvalidArgumentException(invalidArgumentException.getMessage());
        }catch (SizeNotAvailableException sizeNotAvailableException){
            throw new SizeNotAvailableException(sizeNotAvailableException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @DeleteMapping("cart/{userId}/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer userId, @PathVariable Integer itemId){

        try {
            cartService.deleteItem(userId, itemId);
            return new ResponseEntity<>("Item deleted from the cart successfully",
                    HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }

    }

    @PutMapping("cart/{userId}/moveItemFromCartToWishlist/{itemId}")
    public ResponseEntity<String> moveItemFromCartToWishlist(@PathVariable Integer userId,
                                           @PathVariable Integer itemId){
       try {
           cartService.moveItemFromCartToWishlist(userId,itemId);
           return new ResponseEntity<>("Item added to wishlist from the cart successfully",
                   HttpStatus.OK);
       }catch (NotFoundException notFoundException){
           throw new NotFoundException(notFoundException.getMessage());
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

    /*@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "You entered invalid data";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }*/

}
