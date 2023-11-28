package com.aden.malbas.controller;

import com.aden.malbas.dto.CartItemDTO;
import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.dto.WishlistItemDTO;
import com.aden.malbas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartController cartController;
    private final ItemController itemController;
    private final WishlistController wishlistController;

    @GetMapping
    public  ResponseEntity<List<ItemDTO>> malbasMain(){
        String defaultCategory = "women";
        List<ItemDTO> items = itemController.findBy(defaultCategory);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("{category}")
    public  ResponseEntity<List<ItemDTO>> getCategoryItems(@PathVariable String category){
        List<ItemDTO> items = itemController.findBy(category);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserDTO user){
        userService.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PutMapping("editProfile")
    public ResponseEntity<String> editProfile(@RequestBody UserDTO user){
        userService.update(user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("deleteAccount/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer userId){
        userService.deleteAccount(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("{userId}/cart")
    public ResponseEntity<List<CartItemDTO>> getCartItems(@PathVariable Integer userId){
        Integer cartId = userService.getUser(userId).getCart().getId();
        List<CartItemDTO> cartItems = cartController.getCartItem(cartId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PostMapping("{userId}/cart/addItem")
    public ResponseEntity<String> addItemToTheCart(@PathVariable Integer userId,
                                                   @RequestParam Integer itemId,
                                                   @RequestParam  Integer numberOfPieces,
                                                   @RequestParam String size){
        Integer cartId = userService.getUser(userId).getCart().getId();
        cartController.addItemToTheCart(cartId, itemId, numberOfPieces, size);
        return new ResponseEntity<>("Item added to the cart successfully",
                HttpStatus.CREATED);
    }

    @PutMapping("{userId}/cart/updateItem")
    public ResponseEntity<String> updateItemInTheCart(@PathVariable Integer userId,
                                                       @RequestParam Integer itemId,
                                                       @RequestParam(required = false)
                                                       Integer numberOfPieces,
                                                       @RequestParam(required = false)
                                                       String size){
        Integer cartId = userService.getUser(userId).getCart().getId();
        cartController.updateItemInTheCart(cartId, itemId, numberOfPieces, size);
        return new ResponseEntity<>("Item updated in the cart successfully",
                HttpStatus.OK);
    }

    @DeleteMapping("{userId}/cart/deleteItem")
    public ResponseEntity<String> deleteItemFromTheCart(@PathVariable Integer userId,
                                                        @RequestParam Integer itemId){

        Integer cartId = userService.getUser(userId).getCart().getId();
        cartController.deleteItemFromTheCart(cartId, itemId);
        return new ResponseEntity<>("Item deleted from the cart successfully",
                HttpStatus.OK);
    }

    @GetMapping("{userId}/wishlist")
    public ResponseEntity<List<WishlistItemDTO>> getWishlistItems(@PathVariable Integer userId){
        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        List<WishlistItemDTO> wishlistItems = wishlistController.getWishlistItems(wishlistId);
        return new ResponseEntity<>(wishlistItems, HttpStatus.OK);
    }

    @PostMapping("{userId}/wishlist/addItem")
    public ResponseEntity<String> addItemToTheWishlist(@PathVariable Integer userId,
                                                       @RequestParam Integer itemId){

        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        wishlistController.addItemToTheWishlist(wishlistId, itemId);
        return new ResponseEntity<>("Item added to the wishlist successfully",
                HttpStatus.CREATED);
    }

    @DeleteMapping("{userId}/wishlist/deleteItem")
    public ResponseEntity<String> deleteItemFromTheWishlist(@PathVariable Integer userId,
                                                            @RequestParam Integer itemId){

        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        wishlistController.deleteItemFromTheWishList(wishlistId, itemId);
        return new ResponseEntity<>("Item deleted from the wishlist successfully",
                HttpStatus.OK);
    }

    @PutMapping("{userId}/moveItemFromCartToWishlist")
    public void moveItemFromCartToWishlist(@PathVariable Integer userId,
                                           @RequestParam Integer itemId){
        Integer cartId = userService.getUser(userId).getCart().getId();
        Integer wishlistId = userService.getUser(userId).getWishlist().getId();
        cartController.deleteItemFromTheCart(cartId, itemId);
        wishlistController.addItemToTheWishlist(wishlistId, itemId);
    }

    @PostMapping("/admin/addItem")
    public void add(@RequestBody ItemDTO item, @RequestParam String category){
        itemController.add(item, category);
    }

    @PutMapping("/admin/updateItem/{itemId}/addColor")
    public void addColor(@PathVariable Integer itemId, @RequestParam String colorName){
        itemController.addColor(itemId, colorName);
    }

    @PutMapping("/admin/updateItem/{itemId}/addSize")
    public void addSize(@PathVariable Integer itemId, @RequestParam String sizeName){
         itemController.addSize(itemId, sizeName);
    }

    @PutMapping("/admin/updateItem/{itemId}/addSale")
    public void addSale(@PathVariable Integer itemId, @RequestParam Double salePrice){
        itemController.addSale(itemId, salePrice);
    }

    @PutMapping("/admin/updateItem/{itemId}/removeSale")
    public void removeSale(@PathVariable Integer itemId){
        itemController.removeSale(itemId);
    }

    @PutMapping("/admin/updateItem/{itemId}/addPieces")
    public void addPieces(@PathVariable Integer itemId, @RequestParam Integer piecesCount){
        itemController.addPieces(itemId, piecesCount);
    }

    @DeleteMapping("/admin/deleteItem/{itemId}")
    public void deleteItem(@PathVariable Integer itemId){
        itemController.deleteItem(itemId);
    }

}
