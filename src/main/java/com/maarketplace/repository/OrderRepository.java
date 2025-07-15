package com.maarketplace.repository;

import com.maarketplace.model.Order;
import com.maarketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    SELECT DISTINCT
        cli.product.id AS productId,
        cli.product.name AS productName,
        FUNCTION('TO_CHAR', o.insertedAt, 'yyyy-MM-dd') AS insertedAt
    FROM Order o
    JOIN o.cart.cartLineItems cli
    WHERE o.user.id = :userId
    """)
    public List<Object[]> findAllByUserId(Long userId);

    public List<Order> findAllByUserOrderByInsertedAtDesc(User user);
}
