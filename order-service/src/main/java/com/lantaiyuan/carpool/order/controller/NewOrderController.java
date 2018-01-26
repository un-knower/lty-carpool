package com.lantaiyuan.carpool.order.controller;


import com.lantaiyuan.carpool.common.constant.ResultCodeEnum;
import com.lantaiyuan.carpool.common.ResultObject;
import com.lantaiyuan.carpool.order.domain.request.NewOrderRequest;
import com.lantaiyuan.carpool.order.service.INewOrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/15$ 17:59$
 * @description:
 */
@RestController
@Slf4j
public class NewOrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewOrderController.class);
    @Autowired
    private INewOrderService newOrderService;

    @RequestMapping(value = "/order/newOrder", method = RequestMethod.POST)
    public ResultObject newOrder(@RequestBody NewOrderRequest newOrderRequest) {
        int r = newOrderService.newOrder(newOrderRequest);
        ResultObject ret = new ResultObject(ResultCodeEnum.SUCCESS.getValue(), r);
        return ret;
    }
}
