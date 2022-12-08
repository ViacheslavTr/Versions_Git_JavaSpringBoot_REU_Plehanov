package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByTitle(String title);
    // Поиск по части наименования товара в независимости от регистра
    List<Product> findByTitleContainingIgnoreCase(String name);
    // Поиск по части наименования товара и фильтрации по диапазону цен
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <=?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThan(String title, float from, float to);
    // Поиск по части наименования товара и фильтрации по диапазону цен, сортировка по возрастанию
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <=?3) order by price", nativeQuery = true)
    List<Product> findByTitleAndOrderByPrice(String title, float from, float to);
    // Поиск по части наименования товара и фильтрации по диапазону цен, сортировка по убыванию
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <=?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleAndOrderByPriceDesc(String title, float from, float to);
    // Поиск по части наименования товара и фильтрации по диапазону цен, сортировка по возрастанию, фильтрация по категории
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <=?3) and category_id=?4 order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPrice(String title, float from, float to, int category);
    // Поиск по части наименования товара и фильтрации по диапазону цен, сортировка по убыванию, фильтрация по категории
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') or (lower(title) LIKE '%?1')) and (price >= ?2 and price <=?3) and category_id=?4 order by price desc", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float from, float to, int category);
}
