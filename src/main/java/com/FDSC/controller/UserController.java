package com.FDSC.controller;

import cn.hutool.core.util.StrUtil;
import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.User;
import com.FDSC.exception.ServiceException;
import com.FDSC.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.FDSC.controller.dto.UserDTO;
import com.FDSC.controller.dto.UserInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //新增和修改
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        String username=user.getUsername();
        String password = user.getPassword();
        String nickname = user.getNickname();
        //校验
        if(     StrUtil.isBlank(username)||
                StrUtil.isBlank(password)||
                StrUtil.isBlank(nickname)
        ) {
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO userDTO=userService.register(user);
        return Result.success(userDTO);
    }
    /*
     * 登录接口
     * */
    @PostMapping("/login")
    public Result login(@RequestParam String username,@RequestParam String password){
        //校验
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        Map<String,Object>res = userService.login(username,password);
        return Result.success(res);
    }
    //新增和修改
    @PostMapping
    public Result save(@RequestBody User user){
        return Result.success(userService.saveUser(user));
    }


    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam String userId) {
        User user= userService.getById(userId);
        UserDTO u = new UserDTO();
        BeanUtils.copyProperties(user,u);
        return Result.success(u);
    }
    @PostMapping("/upUserInfo")
    public Result upUserInfo(@RequestBody UserInfoDto user) {
        return userService.upUserInfo(user);
    }
    //没有任何用处，仅作为token校验
    @PostMapping("/token")
    public Result token(){return Result.success();}
    @PostMapping("/checktoken")
    public Result token(@RequestParam String userid,
                        @RequestParam String token){
        String userId = "";
        try {
            userId = JWT.decode(token).getAudience().get(0);
            if(userId.equals(userid)) return Result.success("success");
            else return Result.success("failure");
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401,"token验证失败！");
        }

    }
    @PostMapping("/changePassword")
    public  Result changePassword(@RequestParam String userName,
                                  @RequestParam String oldPassword,
                                  @RequestParam String newPassword){
        return Result.success(userService.changePassword(userName,oldPassword,newPassword));
    }







}
