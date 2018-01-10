package cc.mysql.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    public Integer id;

    @Column(name = "customer_id")
    public String customerId;

    @Column(name = "product_id")
    public String productId;

    public Integer time;

    @Column(name = "base_price")
    public Float basePrice;

    @Column(name = "discount_rate")
    public Float discountRate;

    @Column(name = "product_cnt")
    public Integer productCnt;

    @Column(columnDefinition = "tinyint", length = 4)
    public Integer rating;
}
