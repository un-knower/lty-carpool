package com.lantaiyuan.carpool.order.service;


import com.lantaiyuan.carpool.common.domain.request.CancelRequest;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/11$ 9:50$
 * @description:取消订单实现
 */
public interface ICancelService {
    /**
     *
     * @param cancelRequest
     * @return
     */
    int cancel(CancelRequest cancelRequest);
}
