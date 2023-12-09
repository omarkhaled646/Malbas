package com.aden.malbas.controller;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Malbas")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

}
