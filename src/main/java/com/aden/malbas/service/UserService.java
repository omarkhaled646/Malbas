package com.aden.malbas.service;

import com.aden.malbas.model.classes.Cart;
import com.aden.malbas.model.classes.User;
import com.aden.malbas.model.classes.Wishlist;
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
    public void save(User user) {
        user.setCart(new Cart());
        user.setWishlist(new Wishlist());
        user.setOrders(new ArrayList<>());
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        User userFromDB = userRepository.findById(user.getId()).orElse(null);

        if(userFromDB == null){
            throw new NullPointerException();
        }

        user.setCart(userFromDB.getCart());
        user.setWishlist(userFromDB.getWishlist());
        user.setOrders(userFromDB.getOrders());

        userRepository.save(user);
    }

    @Transactional
    public void delete(User user) {

        User userFromDB = userRepository.findById(user.getId()).orElse(null);

        if(userFromDB == null){
            throw new NullPointerException();
        }

        userRepository.deleteById(user.getId());
    }

    public User getUser(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new NullPointerException();
        }

        return user;
    }
}
