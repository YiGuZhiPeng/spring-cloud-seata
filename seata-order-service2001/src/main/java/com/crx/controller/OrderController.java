package com.crx.controller;

import com.crx.domain.CommonResult;
import com.crx.domain.Order;
import com.crx.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order/create")
    public CommonResult create(Order order){
        orderService.create(order);
        //Order order1 = new Order(1L,1L,1L,10, new BigDecimal("100.00"),1);
        return new CommonResult<Order>(200,"订单创建成功",order);
    }
}
