package cc.mysql.controller;

import cc.mysql.repository.CategoryRepository;
import cc.mysql.repository.CustomerRepository;
import cc.mysql.repository.OrderRepository;
import cc.mysql.repository.ProductRepository;
import cc.mysql.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class InfoController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    private Map<String, Object> cache = new HashMap<>();

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object get(@RequestParam(required = false) Boolean useCache) {
        if (useCache != null && !useCache) {
            long customerCnt = customerRepository.count();
            long orderCnt = orderRepository.count();
            long productCnt = productRepository.count();

            cache.put("customerCnt", customerCnt);
            cache.put("orderCnt", orderCnt);
            cache.put("productCnt", productCnt);
            cache.put("categories", categoryRepository.findAll());
        }
        return new Response(200, "OK", cache, false);
    }

    @RequestMapping(value = "/info/year", method = RequestMethod.GET)
    public Object getYearInfo() {
        return categoryRepository.getYearInfo();
    }

    @RequestMapping(value = "/info/month", method = RequestMethod.GET)
    public Object getMonthInfo() {
        return categoryRepository.getMonthInfo();
    }

    @RequestMapping(value = "/info/week", method = RequestMethod.GET)
    public Object getWeekInfo() {
        return categoryRepository.getWeekInfo();
    }

    @RequestMapping(value = "/info/weekday", method = RequestMethod.GET)
    public Object getWeekdayInfo() {
        return categoryRepository.getWeekdayInfo();
    }
}
