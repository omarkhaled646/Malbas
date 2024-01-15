package com.aden.malbas.service;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.model.classes.Cart;
import com.aden.malbas.model.classes.Order;
import com.aden.malbas.model.classes.User;
import com.aden.malbas.model.classes.Wishlist;
import com.aden.malbas.model.enums.AdultSize;
import com.aden.malbas.model.enums.Gender;
import com.aden.malbas.model.enums.Role;
import com.aden.malbas.model.mappers.UserMapper;
import com.aden.malbas.repository.UserRepository;
import com.aden.malbas.request.AuthenticationRequest;
import com.aden.malbas.request.AuthenticationResponse;
import com.aden.malbas.request.RegisterRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) throws IllegalAccessException{
        User user = User
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf(registerRequest.getRole().toUpperCase()))
                .gender(Gender.valueOf(registerRequest.getGender().toUpperCase()))
                .build();

        if(registerRequest.getSize() != null){
            user.setSize(AdultSize.valueOf(registerRequest.getSize().toUpperCase()));
        }

        user.setId(0);
        if(user.getRole() == Role.USER){
            user.setCart(new Cart());
            user.setWishlist(new Wishlist());
        }

        userRepository.save(user);

        // Map<String, Object> claims = convertUserToMap(user);

        var token = jwtService.generateToken(user.getUsername());

        return AuthenticationResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws IllegalAccessException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println("Here");
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        // Map<String, Object> claims = convertUserToMap(user);
        var token = jwtService.generateToken(user.getUsername());
        System.out.println(user);
        return AuthenticationResponse
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().toString().toLowerCase())
                .token(token)
                .build();
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User userFromDB = userRepository.findById(userDTO.getId()).orElse(null);
        User user;
        // TODO: Add custom exception
        if(userFromDB == null){
            throw new NullPointerException();
        }
        user = userMapper.userDTOToUser(userDTO);
        user.setCart(userFromDB.getCart());
        user.setWishlist(userFromDB.getWishlist());

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


    public void add(Order order, Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            throw new NullPointerException();
        }

        user.add(order);
    }

   /* private Map<String, Object> convertUserToMap(User user) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> userClass = user.getClass();

        for (Field field : userClass.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!fieldName.equals("cart") && !fieldName.equals("order") && !fieldName.equals("wishlist")) {
                Object value = field.get(user);
                if (value != null) {
                    map.put(field.getName(), value);
                }
            }
        }

        return map;
    }*/

}
