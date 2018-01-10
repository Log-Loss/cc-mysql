package cc.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(length = 10,name = "product_id")
    public String productId;

    @Column(columnDefinition = "TEXT")
    public String title;

    public Float price;

    @Column(columnDefinition = "TEXT", name = "image_url")
    public String imageUrl;

    @Column(length = 25)
    public String category;

    public Integer rank;

    public Float rating;

    @Column(name = "sold_cnt")
    public Integer soldCnt;

    @Column(name = "inventory_cnt")
    public Integer inventoryCnt;
}
