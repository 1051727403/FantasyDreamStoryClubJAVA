package com.FDSC.utils;

import cn.hutool.core.util.StrUtil;
import com.FDSC.common.Constants;
import com.FDSC.entity.User;
import com.FDSC.exception.ServiceException;
import com.FDSC.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class TokenUtils {
    private static final long EXPIRE_TIME = 120 * 60 * 1000; //120分钟(2小时)
    public static String generateToken(String userId,String password){
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token="";
        token= JWT.create().withAudience(userId) // 将 user id 保存到 token 里面
                .withExpiresAt(date) //2小时后token过期
                .sign(Algorithm.HMAC256(password)); // 以 password 作为 token 的密钥
        return token;

    }

    private static UserService staticUserService;
    @Autowired
    private UserService  userService;

    //项目启动时执行该方法
    @PostConstruct
    public void init(){
        staticUserService=userService;
    }

    /**
     * 获取当前登录用户的信息
     * @return
     * */
    public static User getUserInfo(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("token");
            if(StrUtil.isNotBlank(token)) {
                String aud = JWT.decode(token).getAudience().get(0);
                Integer userId = Integer.valueOf(aud);
                return staticUserService.getById(userId);
            }
        }catch(Exception e){
            throw new ServiceException(Constants.CODE_600,"获取当前登录用户的信息失败！");
            //return null;
        }
        return null;
    }

}
