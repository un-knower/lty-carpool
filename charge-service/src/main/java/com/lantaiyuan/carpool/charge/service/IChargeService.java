package com.lantaiyuan.carpool.charge.service;

import com.lantaiyuan.carpool.charge.domain.request.ChargeRequest;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/17$ 15:36$
 * @description:
 */
public interface IChargeService {
    Double charge(ChargeRequest chargeRequest);
}
