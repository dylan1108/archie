package com.yilami.archie.demo.order.feign;

import com.yilami.archie.common.model.Response;
import com.yilami.archie.demo.order.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Weihua
 * @since 1.0.0
 */
@FeignClient("demo-book")
public interface BookResource {

    @GetMapping("book/{id}")
    Response<Book> findById(@PathVariable("id") String id);

    @GetMapping("book/header/{headerName}")
    Response<String> getHeader(@PathVariable("headerName") String headerName);
}
