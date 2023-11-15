package com.aden.malbas.model.classes;

import com.aden.malbas.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "_order")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "order_id")
    private Integer id;
    @NotNull @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;
    @NotNull @Min(1)
    private Double price;
    @Enumerated(EnumType.STRING)
    private Status orderStatus;

//    public Order(){}
//
//    public Order(User user, List<OrderItem> items, Double price, Status status) {
//        this.user = user;
//        this.items = items;
//        this.price = price;
//        this.status = status;
//    }
}
