package com.aden.malbas.model.classes;

import com.aden.malbas.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_order")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "order_id")
    private Integer id;
    @NotNull @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                                   CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    private Double totalCost;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", totalCost=" + totalCost +
                ", orderStatus=" + status +
                '}';
    }
}
