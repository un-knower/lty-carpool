package com.lantaiyuan.carpool.order.service.impl;

import com.lantaiyuan.carpool.common.constant.OrderStatusEnum;
import com.lantaiyuan.carpool.common.constant.ResultCodeEnum;
import com.lantaiyuan.carpool.common.dao.OrderRepository;
import com.lantaiyuan.carpool.common.dao.UserRepository;
import com.lantaiyuan.carpool.common.domain.Order;
import com.lantaiyuan.carpool.common.domain.User;
import com.lantaiyuan.carpool.common.domain.request.CancelRequest;
import com.lantaiyuan.carpool.order.channel.PublishChannel;
import com.lantaiyuan.carpool.order.service.ICancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/11$ 9:53$
 * @description:
 */
@Service
@Slf4j
public class CancelServiceImpl implements ICancelService {
    @Autowired
    PublishChannel publishChannel;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public int cancel(CancelRequest cancelRequest) {
        if(canCancel(cancelRequest)){
            updateOrder(cancelRequest);
            Message<CancelRequest> msg = MessageBuilder.withPayload(cancelRequest).setHeader("contentType", cancelRequest.getClass().getCanonicalName()).build();
            publishChannel.publish().send(msg);
            return ResultCodeEnum.SUCCESS.getValue();
        }else{
            return ResultCodeEnum.ORDER_CAN_NOT_CANCEL.getValue();
        }
    }

    /**
     * 更新sql订单信息
     * @param cancelRequest
     */
    void updateOrder(CancelRequest cancelRequest){
        User user=userRepository.findOne(cancelRequest.getUserId());
        Order order=orderRepository.findOne(user.getOrderId());
        order.setOrderStatus(OrderStatusEnum.CANCEL.getValue());
        orderRepository.save(order);
    }

    /**
     * 判断订单能否取消
     * @param cancelRequest
     * @return
     */
    boolean canCancel(CancelRequest cancelRequest){
        return true;
    }

}
