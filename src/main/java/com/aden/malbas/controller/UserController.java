package com.aden.malbas.controller;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.exception.ServerException;
import com.aden.malbas.exception.NotFoundException;
import com.aden.malbas.request.AuthenticationRequest;
import com.aden.malbas.request.AuthenticationResponse;
import com.aden.malbas.request.RegisterRequest;
import com.aden.malbas.service.UserService;
import jakarta.validation.Valid;
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
        try {
            userService.update(userDTO);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        }catch (NotFoundException userNotFoundException){
            throw new NotFoundException(userNotFoundException.getMessage());
        }catch (Exception e){
            throw new ServerException("An Error Occurred");
        }
    }

    @DeleteMapping("deleteAccount/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer userId){
        try {
            userService.deleteAccount(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }catch (NotFoundException userNotFoundException){
            throw new NotFoundException(userNotFoundException.getMessage());
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
