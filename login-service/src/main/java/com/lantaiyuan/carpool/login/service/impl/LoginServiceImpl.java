package com.lantaiyuan.carpool.login.service.impl;

import com.lantaiyuan.carpool.common.UserStatusEnum;
import com.lantaiyuan.carpool.common.dao.UserRepository;
import com.lantaiyuan.carpool.common.domain.User;
import com.lantaiyuan.carpool.common.domain.request.LoginRequest;
import com.lantaiyuan.carpool.common.domain.response.Line2User;
import com.lantaiyuan.carpool.common.domain.response.LoginResponse;
import com.lantaiyuan.carpool.login.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2018/1/11$ 9:42$
 * @description:
 */
@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private StringRedisTemplate localRedisTemplate;
    @Autowired
    private UserRepository userRepository;
    @Override
    public LoginResponse getUserStatusOrRecommend(LoginRequest loginRequest) {
        User user=getUser(loginRequest);
        LoginResponse loginResponse =new LoginResponse();
        if(user.getUserStatus().equals( UserStatusEnum.NO_STATUS.getValue())){
            List<Line2User> lines = recommend(loginRequest);
            loginResponse.setLines(lines);

        }else if(user.getUserStatus().equals( UserStatusEnum.MATCH_STATUS.getValue())){
            List<Line2User> lines = getLine(loginRequest);
            loginResponse.setLines(lines);
        }
        return loginResponse;
    }


    /**
     * 查询用户
     * @param loginRequest
     * @return
     */
    User getUser(LoginRequest loginRequest){
        return  userRepository.findOne(loginRequest.getUserId());
    }

    /**
     * 推荐线路
     * @param loginRequest
     * @return
     */
    List<Line2User> recommend(LoginRequest loginRequest){
        List<Line2User> lines = new ArrayList<>();
        return lines;
    }

    /**
     * 获取用户当前所在处线路状况
     * @param loginRequest
     * @return
     */
    List<Line2User> getLine(LoginRequest loginRequest){
        List<Line2User> lines = new ArrayList<>();
        return lines;
    }
}
