package com.aden.malbas.model.classes;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "cart_id")
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items;


}

