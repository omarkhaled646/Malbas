package com.aden.malbas.model.classes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @EmbeddedId
    private OrderItemKey orderItemKey;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    Item item;

    @NotNull @Min(1)
    private Integer numberOfPieces;

    private String size;

    private Double price;

    @Override
    public String toString() {
        return "OrderItem{" +
                ", numberOfPieces=" + numberOfPieces +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }
}
