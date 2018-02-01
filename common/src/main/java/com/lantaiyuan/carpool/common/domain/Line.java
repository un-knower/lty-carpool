package com.lantaiyuan.carpool.common.domain;

import com.lantaiyuan.carpool.common.Validate;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/15$ 11:54$
 * @description:
 */
@Table(name="carpool_line")
@Entity
@Data
public class Line implements Validate,Serializable {
    @Id
    private Long lineId;
    private Long orderId;
    private String cityCode;
    private Integer departTime;
    private String driverId;
    private String busId;
    private String userId;


    public Line() {
    }

    @Override
    public Boolean validate() {
        return true;
    }
}
