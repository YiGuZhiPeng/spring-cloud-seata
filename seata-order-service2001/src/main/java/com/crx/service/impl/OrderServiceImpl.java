package com.crx.service.impl;

import com.crx.dao.OrderDao;
import com.crx.domain.Order;
import com.crx.service.AccountService;
import com.crx.service.OrderService;
import com.crx.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "my_test_tx_group", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("开始创建订单");
        orderDao.create(order);

        log.info("订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("订单微服务开始调用库存，做扣减-end");

        log.info("调用账户，做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("调用账户，做扣减Money-end");

        //修改订单状态
        log.info("修改订单状态");
        orderDao.update(order.getUserId(),0);
        log.info("修改订单状态-end");

        log.info("订单结束！！！");
    }
}
