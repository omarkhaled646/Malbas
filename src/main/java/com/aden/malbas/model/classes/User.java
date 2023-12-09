package com.aden.malbas.model.classes;

import com.aden.malbas.dto.UserDTO;
import com.aden.malbas.model.enums.AdultSize;
import com.aden.malbas.model.enums.Gender;
import com.aden.malbas.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private AdultSize size;
    @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;


    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        // TODO Add custom exception for gender wrong values
        try {
            this.gender = Gender.valueOf(userDTO.getGender().toUpperCase());
            this.size = AdultSize.valueOf(userDTO.getSize().toUpperCase());
            this.role = Role.valueOf(userDTO.getRole().toUpperCase());
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void add(Order order) {

        if(this.orders == null){
            this.orders = new ArrayList<>();
        }

        this.orders.add(order);
    }
}
