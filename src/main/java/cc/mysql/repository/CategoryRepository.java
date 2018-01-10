package cc.mysql.repository;


import cc.mysql.entity.Category;
import cc.mysql.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Override
    long count();

    @Query(nativeQuery = true, value = "SELECT year(day),month(day),SUM(count) FROM day_info GROUP BY year(day),month(day)")
    List<Object> getMonthInfo();

    @Query(nativeQuery = true, value = "SELECT week(day),weekday(day),SUM(count) FROM day_info GROUP BY  week(day),weekday(day)")
    List<Object> getWeekInfo();

    @Query(nativeQuery = true, value = "SELECT weekday(day),SUM(count) FROM day_info GROUP BY weekday(day)")
    List<Object> getWeekdayInfo();

    @Query(nativeQuery = true, value = "SELECT year(day),SUM(count) FROM day_info GROUP BY  year(day)")
    List<Object> getYearInfo();
}