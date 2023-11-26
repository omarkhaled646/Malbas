package com.aden.malbas.repository;

import com.aden.malbas.model.classes.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
}
