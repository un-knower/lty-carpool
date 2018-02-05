package com.lantaiyuan.carpool.match.service.impl;

import com.lantaiyuan.carpool.common.constant.RedisPoolKey;
import com.lantaiyuan.carpool.common.constant.UserStatusEnum;
import com.lantaiyuan.carpool.common.domain.Line;
import com.lantaiyuan.carpool.common.domain.Order;
import com.lantaiyuan.carpool.common.domain.User;
import com.lantaiyuan.carpool.common.domain.request.CancelRequest;
import com.lantaiyuan.carpool.common.service.IOrderService;
import com.lantaiyuan.carpool.match.service.IMatchService;
import com.robert.vesta.service.intf.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


/**
 * @author qiuwanshan
 */
@Service
@ImportResource("spring/vesta-service-sample.xml")
@Slf4j
public class MatchServiceImpl implements IMatchService {
    final int MIN_ORDER_SIMILAR=80;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private StringRedisTemplate localRedisTemplate;
    private BoundHashOperations<String,Long, Set<Long>> linePool = localRedisTemplate.boundHashOps(RedisPoolKey.linePoolKey);
    private BoundHashOperations<String, Long, Order> orderPool = localRedisTemplate.boundHashOps(RedisPoolKey.orderPoolKey);
    private BoundHashOperations<String, String, User> userPool = localRedisTemplate.boundHashOps(RedisPoolKey.userPoolKey);
    private BoundGeoOperations<String, String> tourPool = localRedisTemplate.boundGeoOps(RedisPoolKey.tourPoolKey);
    @Autowired
    private IdService idService;


    @Override
    public void matchCancel(CancelRequest cancelRequest) {
        User user= userPool.get(cancelRequest.getUserId());
        Long lineId=user.getLineId();
        Long orderId=user.getOrderId();
        linePool.get(lineId).remove(orderId);
        if(linePool.get(lineId).size()==0){
            linePool.delete(lineId);
        }
        orderPool.delete(orderId);
        tourPool.geoRemove(""+orderId+"-"+lineId);
        userPool.delete(user.getUserId());
    }

    @Override
    public void matchOrder(Order order) {
        //获取起始点
        Point point = new Point(order.getStartLongitude(), order.getStartLatitude());
        Long orderId=order.getOrderId();
        Long lineId;
        //新订单，没有线路id
        if (0 == order.getLineId()) {
            lineId = realMatch(order);
            //没有合适线路，为该订单生成新线路
            if (lineId == 0) {
                //生成线路id
                lineId = idService.genId();
            }
            //为订单设置线路id
            order.setLineId(lineId);
        }
        //有线路id，说明是加入某条线路
        else {
            //将订单id添加到线路缓存
            lineId = order.getLineId();
        }
        //保存该订单到缓存
        orderPool.put(order.getOrderId(), order);
        //为行程指定id并缓存
        tourPool.geoAdd(point, ""+orderId+"-"+lineId);
        User user= new User();
        user.setUserId(order.getUserId());
        user.setLineId(lineId);
        user.setOrderId(orderId);
        user.setUserStatus(UserStatusEnum.MATCH_STATUS.getValue());
        userPool.put(user.getUserId(),user);
    }

    /**
     * 撮合合适的线路
     * @param order
     * @return
     */
    private Long realMatch(Order order) {
        int maxSimilarity=0;
        Long similarOrderId=0L;
        //获取附近行程
        Point point = new Point(order.getStartLongitude(), order.getStartLatitude());
        Circle within = new Circle(point,new Distance(100));
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = tourPool.geoRadius(within);
        Iterator<GeoResult<RedisGeoCommands.GeoLocation<String>>> it = results.iterator();
        while (it.hasNext()){
            Long orderId=Long.parseLong(it.next().getContent().getName().split("-")[0]);
            int similarity=orderService.similarity(order,orderPool.get(orderId));
            if(similarity>maxSimilarity){
                maxSimilarity=similarity;
                similarOrderId=orderId;
            }
        }
        //有合适的相似订单
        if(similarOrderId!=0&&maxSimilarity>MIN_ORDER_SIMILAR){
            return orderPool.get(similarOrderId).getLineId();
        }
        return 0L;
    }
}
