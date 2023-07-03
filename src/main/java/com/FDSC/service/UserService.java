package com.FDSC.service;

import cn.hutool.core.util.StrUtil;
import com.FDSC.common.Constants;
import com.FDSC.common.Result;
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
import java.util.List;
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
    private User getUserInfo(String userId,String password){
        QueryWrapper queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("username",userId);
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


    public Result upUserInfo(UserInfoDto user) {
            User u = new User();
            //BeanUtils.copyProperties(user,u);
            u.setUsername(user.getUserName());
            u.setNickname(user.getNickName());
            u.setAvatarUrl(user.getAvatarUrl());
            u.setRemoved(user.getRemoved());
            u.setPassword(user.getPassword());
            if(user.getId() == null ){
                try {
                    return Result.success(userMapper.saveuser(u));
                }
                catch (Exception e){
                    return Result.error(Constants.CODE_500, "注册失败,重复用户名");
                }
            }else {
                u.setId(user.getId());
                try {
                    return Result.success(updateById(u));
                }
                catch (Exception e){
                    return Result.error(Constants.CODE_500, "ID失败");
                }
            }

    }

    public boolean changepw(String userName, String password, String newpassword) {
        User one= getUserInfo(userName,password);
        if(one!=null){
            one.setPassword(newpassword);
            updateById(one);
        }
        else{
            throw  new ServiceException(Constants.CODE_500,"密码错误");
        }

        return true;
    }

    public boolean saveAvatar(Long userid, String avatarUrl) {
        try{
            userMapper.saveAvatar(userid,avatarUrl);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean saveUserName(Long userid, String userName, String nickName) {
        try{
            userMapper.saveUserName(userid,userName,nickName);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public Result deleteUser(String userId) {
        try{
            User one = getById(userId);
            one.setRemoved(1);
            saveOrUpdate(one);
            return Result.success();
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }

    public Result deleteBatchUser(List<String> userId) {
        try{
            return Result.success(userMapper.deleteBatchUser(userId));
        }catch (Exception e){
            return Result.error(Constants.CODE_500,e.getMessage());
        }
    }
}
