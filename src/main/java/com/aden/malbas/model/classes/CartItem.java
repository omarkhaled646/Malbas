package com.aden.malbas.model.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

    @EmbeddedId
    private CartItemKey cartItemKey;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    Item item;

    private Integer numberOfPieces;

    private String size;

    @Override
    public String toString() {
        return "CartItem{" +
                ", item=" + item +
                ", numberOfPieces=" + numberOfPieces +
                ", size='" + size + '\'' +
                '}';
    }
}
