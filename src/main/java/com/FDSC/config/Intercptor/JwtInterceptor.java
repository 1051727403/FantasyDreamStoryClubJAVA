package com.FDSC.config.Intercptor;

import cn.hutool.core.util.StrUtil;
import com.FDSC.common.Constants;
import com.FDSC.entity.User;
import com.FDSC.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.FDSC.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        System.out.println("---------------------------------------------------------------------"+token);
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        //验证token是否为空
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录！");//权限验证失败
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401,"token验证失败！");
        }
        //从数据库中获取用户数据
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_600,"用户不存在，请重新登录!");
        }
        // 根据密码验签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录!");
        }
        return true;
    }
}
