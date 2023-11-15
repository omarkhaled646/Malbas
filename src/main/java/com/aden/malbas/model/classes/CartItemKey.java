package com.aden.malbas.model.classes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class CartItemKey implements Serializable {

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "item_id")
    private Integer itemId;
}
