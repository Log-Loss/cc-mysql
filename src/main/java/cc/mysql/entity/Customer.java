package cc.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(length = 21, name = "customer_id")
    public String customerId;

    @Column(name = "order_cnt")
    public Integer orderCnt;
}
