package com.lantaiyuan.carpool.common.constant;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/11$ 10:01$
 * @description:
 */
public enum UserStatusEnum {
    /**
     * 空状态
     * 订单提交状态
     * 撮合中状态
     * 已发车状态
     */
    NO_STATUS(101),ORDER_STATUS(102),MATCH_STATUS(103),BUS_START_STATUS(104);
    Integer value;

    UserStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
