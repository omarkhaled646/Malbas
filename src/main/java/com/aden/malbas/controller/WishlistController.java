package com.aden.malbas.controller;

import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.exception.InvalidArgumentException;
import com.aden.malbas.exception.NotFoundException;
import com.aden.malbas.exception.ServerException;
import com.aden.malbas.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("wishlist/{userId}")
    public ResponseEntity<List<WishlistItemDTO>> getWishlistItems(@PathVariable Integer userId){
        try {
            List<WishlistItemDTO> wishlistItemDTOs = wishlistService.getWishlistItems(userId);
            return new ResponseEntity<>(wishlistItemDTOs, HttpStatus.OK);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @PostMapping("wishlist/{userId}/addItem/{itemId}")
    public ResponseEntity<String> addItem(@PathVariable Integer userId,
                                          @PathVariable Integer itemId){
        try {
            wishlistService.addItem(userId, itemId);
            return new ResponseEntity<>("Item added to the wishlist successfully",
                    HttpStatus.CREATED);
        }catch (NotFoundException notFoundException){
            throw new NotFoundException(notFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @DeleteMapping("wishlist/{userId}/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer userId,
                                             @PathVariable Integer itemId){
        try {
            wishlistService.deleteItem(userId, itemId);
            return new ResponseEntity<>("Item deleted from the wishlist successfully",
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> serverExceptionHandler(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
