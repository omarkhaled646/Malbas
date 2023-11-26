package com.aden.malbas.controller;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CartController cartController;
    private final ItemController itemController;

    private final String defaultCategory = "women";

    @GetMapping
    public  ResponseEntity<List<ItemDTO>> malbasMain(){
        List<ItemDTO> items = itemController.findBy(defaultCategory);
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

    @PostMapping("{userId}/cart/add")
    public ResponseEntity<String> addItemToTheCart(@PathVariable Integer userId,
                                                   @RequestParam Integer itemId,
                                                   @RequestParam  Integer itemCount,
                                                   @RequestParam String sizeName){
        Integer cartId = userService.getUser(userId).getCart().getId();
        cartController.addItemToTheCart(cartId, itemId, itemCount, sizeName);
        return new ResponseEntity<>("Items added to the cart successfully",
                HttpStatus.CREATED);
    }

    @PostMapping("/admin/addItem")
    public void add(@RequestBody ItemDTO item, @RequestParam String category,
                               @RequestParam List<String> availableSizes){
        itemController.add(item, category, availableSizes);
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
