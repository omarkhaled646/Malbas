package com.aden.malbas.model.classes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemKey implements Serializable {

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "item_id")
    private Integer itemId;
}
