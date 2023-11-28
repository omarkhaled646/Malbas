package com.aden.malbas.repository;

import com.aden.malbas.model.classes.CartItem;
import com.aden.malbas.model.classes.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartITemRepository extends JpaRepository<CartItem, CartItemKey> {

    @Query("SELECT ci.item.id FROM CartItem ci WHERE ci.cart.id = :cartId")
    List<Integer> findCartItems(@Param("cartId") Integer cartId);

}
