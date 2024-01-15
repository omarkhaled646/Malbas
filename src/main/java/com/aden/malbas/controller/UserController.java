package com.aden.malbas.controller;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.request.AuthenticationRequest;
import com.aden.malbas.request.AuthenticationResponse;
import com.aden.malbas.request.RegisterRequest;
import com.aden.malbas.service.UserService;
import jakarta.validation.Valid;
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


    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest)
            throws IllegalAccessException{
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
        }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) throws IllegalAccessException {
        return new ResponseEntity<>(userService.authenticate(request), HttpStatus.OK);
    }

    @PutMapping("editProfile")
    public ResponseEntity<String> editProfile(@Valid @RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("deleteAccount/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer userId){
        userService.deleteAccount(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
