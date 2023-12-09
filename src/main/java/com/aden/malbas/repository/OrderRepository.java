package com.aden.malbas.repository;

import com.aden.malbas.model.classes.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findOrdersBy(@Param("userId") Integer userId);
}
