package com.aden.malbas.repository;

import com.aden.malbas.model.classes.OrderItem;
import com.aden.malbas.model.classes.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {

    @Query(value = "SELECT ot FROM OrderItem ot WHERE ot.order.id = :orderId")
    List<OrderItem> findBy(@Param("orderId") Integer orderId);
}
