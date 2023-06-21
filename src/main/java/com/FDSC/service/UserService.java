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
            if(user.getId()==null){
                //没有id代表新增
                try {
                    //检查是否重名
                    if (userMapper.checkUser(user.getUsername())) {
                        throw new ServiceException(Constants.CODE_500, "用户名已存在，保存失败！");
                    }
                }catch (ServiceException se){
                    throw new ServiceException(Constants.CODE_500, "用户名已存在，保存失败！");
                }
                user.setIsAdmin("0");
                return save(user);
            }else{
                //有id代表更新
                return updateById(user);
            }
        }catch (Exception se){
            throw new ServiceException(Constants.CODE_500,"用户数据保存失败！");
        }

        //return saveOrUpdate(user);
    }

    public Map<String, Object> login(String username, String password) {
        User one=getUserInfo(username,password);
        if(one!=null) {
            Map<String,Object>res=new HashMap<>();
            UserDTO userDTO=new UserDTO();
            //设置token
            String token = TokenUtils.generateToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            BeanUtils.copyProperties(one, userDTO);//将user内容copy到userdto中去
            res.put("data",userDTO);
            //判断是否为管理员、
            log.info(one.getIsAdmin());
            if(one.getIsAdmin().equals("0")){
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
            save(user);
            BeanUtils.copyProperties(user,userDTO);
            //头像不用设置
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600,"该用户已被注册！");
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
            if(u.getId()!=null){
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

//    @Autowired
//    private UserMapper userMapper;

//    public int save(User user){
//        if(user.getId()==null){
//            //没有id代表新增
//            return userMapper.insert(user);
//        }else{
//            //有id代表更新
//            return userMapper.update(user);
//        }
//    }
}
