package cc.mysql.controller;

import cc.mysql.entity.Product;
import cc.mysql.repository.ProductRepository;
import cc.mysql.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public Object get(@RequestParam(required = false) String productId) {
        Object result;
        if (productId == null) {
            result = productRepository.findAll();
        } else {
            result = productRepository.findOne(productId);
        }
        return new Response(200, "OK", result, false);
    }

    @RequestMapping(value = "/product/query", method = RequestMethod.POST)
    public Object query(@RequestBody Map<String, String> body) {
        Object result = null;
        if (body.containsKey("title") && body.containsKey("category")) {
            String title = body.get("title");
            String category = body.get("category");
            result = productRepository.findAllByTitleAndCategory(title, category);
        } else if (body.containsKey("title")) {
            String title = body.get("title");
            result = productRepository.findAllByTitle(title);
        } else if (body.containsKey("category")) {
            String category = body.get("category");
            result = productRepository.findAllByCategory(category);
        }


        return new Response(200, "OK", result, false);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public Object post(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        Float price = Float.valueOf(body.get("price"));
        String imageUrl = body.get("imageUrl");
        String category = body.get("category");
        Integer inventoryCnt = Integer.valueOf(body.get("inventoryCnt"));

        Product product = new Product();
        product.productId = getRandomString(10);
        product.title = title;
        product.price = price;
        product.imageUrl = imageUrl;
        product.category = category;
        product.inventoryCnt = inventoryCnt;
        product.soldCnt = 0;
        productRepository.saveAndFlush(product);
        return new Response(200, "OK", product, false);
    }

    @RequestMapping(value = "/product", method = RequestMethod.PUT)
    public Object put(@RequestBody Map<String, String> body) {
        String productId = body.get("productId");
        String title = body.get("title");
        Float price = Float.valueOf(body.get("price"));
        String imageUrl = body.get("imageUrl");
        String category = body.get("category");
        Integer inventoryCnt = Integer.valueOf(body.get("inventoryCnt"));

        Product product = productRepository.findOne(productId);
        product.title = title;
        product.price = price;
        product.imageUrl = imageUrl;
        product.category = category;
        product.inventoryCnt = inventoryCnt;
        productRepository.saveAndFlush(product);
        return new Response(200, "OK", product, false);
    }

    @RequestMapping(value = "/product", method = RequestMethod.DELETE)
    public Object delete(@RequestParam(required = false) String productId) {
        if (!productRepository.exists(productId)) {
            return new Response(404, "product not fount!", null, false);
        }
        productRepository.delete(productId);
        return new Response(200, "OK", null, false);
    }
}
