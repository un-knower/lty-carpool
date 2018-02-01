package com.lantaiyuan.carpool.common.domain;

import com.lantaiyuan.carpool.common.Validate;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/15$ 15:52$
 * @description:
 */
@Table(name="carpool_driver")
@Entity
@Data
public class Driver implements Validate,Serializable {
    @Id
    private String driverId;
    private String driverName;
    private Integer driverJudgement;
    private String driverPhoneNumber;

    public Driver() {
    }

    @Override
    public Boolean validate() {
        return true;
    }
}
