package cc.mysql.repository;


import cc.mysql.entity.Category;
import cc.mysql.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Override
    long count();
}