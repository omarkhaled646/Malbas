package com.aden.malbas.repository;

import com.aden.malbas.model.classes.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item findByName(String name);

    @Query(value = "SELECT * FROM Item WHERE category = :categoryValue", nativeQuery = true)
    List<Item> findByCategory(@Param("categoryValue") String defaultCategory);
}
