package com.aden.malbas.repository;

import com.aden.malbas.model.classes.CartItem;
import com.aden.malbas.model.classes.CartItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartITemRepository extends JpaRepository<CartItem, CartItemKey> {
}
