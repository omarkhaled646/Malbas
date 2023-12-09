package com.aden.malbas.model.classes;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "wishlist_id")
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "wishlist_item",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

    public void add(Item item) {

        if(this.items == null){
            this.items = new ArrayList<>();
        }

        this.items.add(item);
    }

    public void delete(Item item) {

        this.items.remove(item);
    }
}
