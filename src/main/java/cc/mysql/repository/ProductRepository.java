package cc.mysql.repository;


import cc.mysql.entity.Order;
import cc.mysql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    public Product findByProductId(String productId);

    @Query(nativeQuery = true, value = "SELECT * FROM products  WHERE category=:category LIMIT 200")
    public List<Product> findAllByCategory(String category);

    @Query(nativeQuery = true, value = "SELECT * FROM products  WHERE title=:title LIMIT 200")
    public List<Product> findAllByTitle(String title);

    public List<Product> findAllByTitleAndCategory(String title, String category);

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM products LIMIT 200")
    List<Product> findAll();

    @Override
    long count();
}