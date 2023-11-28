package com.aden.malbas.service;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.model.classes.Cart;
import com.aden.malbas.model.classes.User;
import com.aden.malbas.model.classes.Wishlist;
import com.aden.malbas.model.enums.Role;
import com.aden.malbas.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setId(0);
        if(user.getRole() == Role.USER){
            user.setCart(new Cart());
            user.setWishlist(new Wishlist());
            user.setOrders(new ArrayList<>());
        }
        userRepository.save(user);
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User userFromDB = userRepository.findById(userDTO.getId()).orElse(null);
        User user;
        // TODO: Add custom exception
        if(userFromDB == null){
            throw new NullPointerException();
        }
        user = new User(userDTO);
        user.setCart(userFromDB.getCart());
        user.setWishlist(userFromDB.getWishlist());
        user.setOrders(userFromDB.getOrders());

        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(Integer userId) {
        userRepository.deleteById(userId);
    }

    public User getUser(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        // TODO: Add custom exception
        if(user == null){
            throw new NullPointerException();
        }

        return user;
    }
}
