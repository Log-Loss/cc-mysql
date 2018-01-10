package cc.mysql.repository;


import cc.mysql.entity.Customer;
import cc.mysql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM customers LIMIT 200")
    List<Customer> findAll();

    @Override
    long count();
}