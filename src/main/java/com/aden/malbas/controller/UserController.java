package com.aden.malbas.controller;

import com.aden.malbas.dto.ItemDTO;
import com.aden.malbas.model.classes.User;
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

    @GetMapping
    public String malbasMain(){
        return "Welcome, to Malbas.";
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user){
        user.setId(0);
        userService.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PutMapping("editProfile")
    public ResponseEntity<String> editProfile(@RequestBody User user){
        userService.update(user);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestBody User user){
        userService.delete(user);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PostMapping("{userId}")
    public ResponseEntity<String> addItemToTheCart(@PathVariable Integer userId,
                                                   @RequestParam Integer itemId,
                                                   @RequestParam  Integer itemCount){
        Integer cartId = userService.getUser(userId).getCart().getId();
        cartController.addItemToTheCart(cartId, itemId, itemCount);
        return new ResponseEntity<>("Items added to the cart successfully",
                HttpStatus.OK);
    }

    @PostMapping("/admin")
    public void addItemAsAdmin(@RequestBody ItemDTO item, @RequestParam String category,
                               @RequestParam List<String> availableSizes){
        itemController.addItem(item, category, availableSizes);
    }

}
