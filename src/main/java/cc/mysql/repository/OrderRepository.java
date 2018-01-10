package cc.mysql.repository;


import cc.mysql.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByProductId(String productId);

    List<Order> findAllByCustomerId(String customerId);

    List<Order> findAllByProductIdAndCustomerId(String productId, String customerId);

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM orders LIMIT 200")
    List<Order> findAll();

    @Override
    long count();
}