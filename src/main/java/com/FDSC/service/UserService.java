package com.FDSC.service;

import cn.hutool.core.util.StrUtil;
import com.FDSC.common.Constants;
import com.FDSC.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.FDSC.controller.dto.UserDTO;
import com.FDSC.controller.dto.UserInfoDto;
import com.FDSC.exception.ServiceException;
import com.FDSC.mapper.UserMapper;
import com.FDSC.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private UserMapper userMapper;

    public boolean saveUser(User user){
        try{
            try {
                //检查是否重名
                if (userMapper.checkUser(user.getUsername())) {
                    throw new ServiceException(Constants.CODE_500, "用户名已存在，保存失败！");
                }
            }catch (ServiceException se){
                throw new ServiceException(Constants.CODE_500, "用户名已存在，保存失败！");
            }
            return save(user);
        }catch (Exception se){
            throw new ServiceException(Constants.CODE_500,"用户数据保存失败！");
        }
    }

    public Map<String, Object> login(String username, String password) {
        User one=getUserInfo(username,password);
        if(one!=null) {
            Map<String,Object>res=new HashMap<>();
            UserDTO userDTO=new UserDTO();
            //设置token
            String token = TokenUtils.generateToken(String.valueOf(one.getId()), one.getPassword());
            userDTO.setToken(token);
            BeanUtils.copyProperties(one, userDTO);//将user内容copy到userdto中去
            res.put("data",userDTO);
            //判断是否为管理员、
            if(one.getIsAdmin().equals(0)){
                //用户登录
                res.put("is_admin",0);
            }else {
                //管理员登录
                res.put("is_admin",1);
            }
            return res;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    public UserDTO register(User user) {
        User one=getUserInfo(user.getUsername(),"");
        UserDTO userDTO=new UserDTO();
        if(one==null){
            userMapper.saveuser(user);
            BeanUtils.copyProperties(user,userDTO);
            //头像不用设置
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600,"该用户名已被注册！");
        }
    }
    private User getUserInfo(String username,String password){
        QueryWrapper queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("username",username);
        if(!StrUtil.isBlank(password)) {
            queryWrapper.eq("password", password);
        }
        User one;
        try {
            one= getOne(queryWrapper);
            return one;
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException(Constants.CODE_500,"用户名或密码错误");
        }
    }

    public UserInfoDto getUserInfoDtoInfo(String username) {
        QueryWrapper queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("username",username);
        UserInfoDto uds = new UserInfoDto();
        User one;
        try{
            one = getOne(queryWrapper);
            BeanUtils.copyProperties(one,uds);
            return uds;
        }
        catch(Exception e){
            throw new ServiceException(Constants.CODE_500,"无用户");
        }
    }

    public boolean upUserInfo(UserInfoDto user) {
            User u = new User();
            BeanUtils.copyProperties(user,u);
            if(u.getId()>0){
                try {
                    updateById(u);
                }
                catch (Exception e){
                    throw new ServiceException(Constants.CODE_500, "ID失败");
                }
            }else {
                throw new ServiceException(Constants.CODE_500, "保存失败");
            }
            return true;

    }

    public boolean changepw(String username, String password, String newpassword) {
        User one=getUserInfo(username,password);
        if(one!=null){
            one.setPassword(newpassword);
            updateById(one);
        }
        else{
            throw  new ServiceException(Constants.CODE_500,"用户名错误");
        }

        return true;
    }
}
