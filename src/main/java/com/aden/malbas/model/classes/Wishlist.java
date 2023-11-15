package com.aden.malbas.model.classes;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "wishlist_id")
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,
                                                   CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "wishlist_item",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;
}
