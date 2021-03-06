package com.yilami.archie.demo.order.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yilami.archie.common.model.Response;
import com.yilami.archie.demo.order.feign.BookResource;
import com.yilami.archie.demo.order.model.Book;
import com.yilami.archie.demo.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Weihua
 * @since 1.0.0
 */
@RequestMapping("order")
@RestController
@RefreshScope
public class OrderController {

    private static final Map<String, Order> ORDERS = new HashMap<>();

    @Autowired
    private BookResource bookResource;

//    @PostConstruct
//    public void init(){
//        List<FlowRule> rules = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("findById");
//        // set limit qps to 20
//        rule.setCount(7);
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//    }

    @GetMapping("header/{headerName}")
    public Response<String> getHeader(@PathVariable String headerName){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return Response.success(headerName + "=" + request.getHeader(headerName) + " -> " + bookResource.getHeader(headerName).getData());
    }

    @PostMapping
    public Response<Order> save(Order order){
        order.setId(UUID.randomUUID().toString());
        ORDERS.put(order.getId(), order);
        return Response.success(order);
    }

    @GetMapping("book/{id}")
    public Response<Book> findBookById(@PathVariable String id){
        return bookResource.findById(id);
    }

    @GetMapping("/{id}")
    public Response<Order> findById(@PathVariable String id) {
        System.out.println("Pass:" + new Date());
        return Response.success(ORDERS.get(id));
    }

    @GetMapping
    public Response<Collection<Order>> findAll(){
        return Response.success(ORDERS.values());
    }

    @ExceptionHandler
    public Response<String> handleException(BlockException ex){
        System.out.println("Limited:" + new Date());
        //|--timestamp-|------date time----|-resource-|p |block|s |e|rt  |occupied
        //p stands for incoming request,
        // block for blocked by rules,
        // success for success handled by Sentinel,
        // e for exception count,
        // rt for average response time (ms),
        // occupied stands for occupiedPassQps since 1.5.0 which enable us booking more than 1 shot when entering.
        return Response.failure(ex.getMessage());
    }

}
