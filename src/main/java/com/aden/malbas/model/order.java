package com.aden.malbas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull @NotEmpty
    private User user;
    @NotNull @NotEmpty
    private List<Item> orderItems;
    @NotNull @NotEmpty
    private Double price;
    @NotNull @NotEmpty
    private Status status;

    public order(){}

    public order(User user, List<Item> orderItems, Double price, Status status) {
        this.user = user;
        this.orderItems = orderItems;
        this.price = price;
        this.status = status;
    }
}
