package com.FDSC.mapper;

import com.FDSC.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

// @Mapper 配置放在配置类config最好不放在启动类
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Update("Update user " +
            "set avatar_url=#{avatarUrl} " +
            "where id=#{userid}")
    boolean saveAvatar(Long userid, String avatarUrl);

    @Select("select count(*) from sys_user where username=#{username}")
      boolean checkUser(String username);

      @Insert("Insert into user(username,password,nickname,avatar_url) values" +
              "(#{username},#{password},#{nickname},#{avatarUrl})")
      boolean saveuser(User user);
    @Update("Update user " +
            "set username=#{userName}, " +
            "nickname=#{nickName} " +
            "where id=#{userid}")
    boolean saveUserName(Long userid, String userName, String nickName);

    Object deleteBatchUser(List<String> userId);


//    @Select("SELECT * from sys_user")
//    List<User> findAll();
//
//    @Select("select * from sys_user where username like concat('%',#{username},'%') limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize,String username);//和mybitis-plus框架同名
//
//    @Select("select count(*) from sys_user where username like concat('%',#{username},'%')")
//    Integer selectCount(String username);
//
//    //@Insert("INSERT INTO sys_user(username,password,nickname,email,phone,address) VALUES(#{username},#{password},#{nickname},#{email},#{phone},#{address})")
//    //int insert(User user);
//
//    //int update(User user);
//
//    @Delete("delete from sys_user where id=#{id}")
//    Integer  deleteById(@Param("id") Integer id);



}
