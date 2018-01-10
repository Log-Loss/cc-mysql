package cc.mysql.controller;

import cc.mysql.entity.Customer;
import cc.mysql.entity.Order;
import cc.mysql.entity.Product;
import cc.mysql.repository.CustomerRepository;
import cc.mysql.repository.OrderRepository;
import cc.mysql.repository.ProductRepository;
import cc.mysql.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public Object get(@RequestParam(required = false) Integer orderId) {

        Object result;
        if (orderId == null) {
            result = orderRepository.findAll();
        } else {
            result = orderRepository.findOne(orderId);
        }
        return new Response(200, "OK", result, false);
    }

    @RequestMapping(value = "/order/query", method = RequestMethod.POST)
    public Object query(@RequestBody Map<String, String> body) {
        Object result = null;
        if (body.containsKey("customerId") && body.containsKey("productId")) {
            String customerId = body.get("customerId");
            String productId = body.get("productId");
            result = orderRepository.findAllByProductIdAndCustomerId(productId, customerId);
        }
        if (body.containsKey("customerId")) {
            String customerId = body.get("customerId");
            result = orderRepository.findAllByCustomerId(customerId);
        }
        if (body.containsKey("productId")) {
            String productId = body.get("productId");
            result = orderRepository.findAllByProductId(productId);
        }

        return new Response(200, "OK", result, false);
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Object post(@RequestBody Map<String, String> body) {
        String customerId = body.get("customerId");
        String productId = body.get("productId");
        Float discountRate = Float.valueOf(body.get("discountRate"));
        Integer productCnt = Integer.valueOf(body.get("productCnt"));

        Order order = new Order();
        order.customerId = customerId;
        order.productId = productId;
        order.time = new Long(new Date().getTime() / 1000).intValue();
        order.discountRate = discountRate;
        order.productCnt = productCnt;


        Product product = productRepository.findOne(productId);
        Customer customer = customerRepository.findOne(customerId);

        order.basePrice = product.price;

        product.soldCnt = product.soldCnt + productCnt;
        product.inventoryCnt = product.inventoryCnt - productCnt;
        customer.orderCnt = customer.orderCnt + productCnt;

        if (product.inventoryCnt < productCnt) {
            return new Response(500, "product is not enough", null, false);
        }

        productRepository.saveAndFlush(product);
        customerRepository.saveAndFlush(customer);

        orderRepository.saveAndFlush(order);
        return new Response(200, "OK", order, false);
    }

    @RequestMapping(value = "/order", method = RequestMethod.PUT)
    public Object put(@RequestBody Map<String, String> body) {
        Integer orderId = Integer.valueOf(body.get("orderId"));
        String customerId = body.get("customerId");
        String productId = body.get("productId");
        Integer time = Integer.valueOf(body.get("time"));
        Float discountRate = Float.valueOf(body.get("discountRate"));
        Integer productCnt = Integer.valueOf(body.get("productCnt"));
        Integer rating = Integer.valueOf(body.get("rating"));

        Order order = orderRepository.findOne(orderId);
        order.customerId = customerId;
        order.productId = productId;
        order.time = time;
        order.discountRate = discountRate;
        order.productCnt = productCnt;
        order.rating = rating;
        orderRepository.saveAndFlush(order);

        return new Response(200, "OK", order, false);
    }

    @RequestMapping(value = "/order", method = RequestMethod.DELETE)
    public Object delete(@RequestParam(required = false) Integer orderId) {
        if (!orderRepository.exists(orderId)) {
            return new Response(404, "order not fount!", null, false);
        }
        orderRepository.delete(orderId);
        return new Response(200, "OK", null, false);
    }

}
