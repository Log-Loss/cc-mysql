package cc.mysql.controller;

import cc.mysql.entity.Customer;
import cc.mysql.repository.CustomerRepository;
import cc.mysql.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public Object get(@RequestParam(required = false) String customerId) {
        Object result;
        if (customerId == null) {
            result = customerRepository.findAll();
        } else {
            result = customerRepository.findOne(customerId);
        }
        return new Response(200, "OK", result, false);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public Object post(@RequestBody Map<String, String> body) {
        String id = body.get("customerId");
        Customer customer = new Customer();
        customer.customerId = id;
        customer.orderCnt = 0;
        customerRepository.saveAndFlush(customer);
        return new Response(200, "OK", customer, false);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public Object post(@RequestParam String customerId) {
        if (!customerRepository.exists(customerId)) {
            return new Response(404, "customer not found!", null, false);
        }
        customerRepository.delete(customerId);
        return new Response(200, "OK", null, false);
    }

}
