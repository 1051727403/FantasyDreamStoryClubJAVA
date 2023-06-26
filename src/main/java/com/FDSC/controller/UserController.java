package com.FDSC.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.User;
import com.FDSC.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.FDSC.controller.dto.UserDTO;
import com.FDSC.controller.dto.UserInfoDto;
import com.FDSC.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
        Integer isAdmin=user.getIsAdmin();
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

    //查询所有表格信息
    @GetMapping
    public List<User> findAll(){
        return userService.list();
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }
    //批量删除
    @PostMapping("/del/bash")
    public boolean deleteBash(@RequestBody List<Integer> ids){
        return userService.removeByIds(ids);
    }

    //分页查询手写
    //@RequestParam 将接收到的  ?pageNum=1?pageSize=10类型的进行映射
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String username){
//        pageNum=(pageNum-1)*pageSize;
//        Integer count=userMapper.selectCount(username);
//        Map<String,Object>res=new HashMap<>();
//        List<User>data=userMapper.selectPage(pageNum,pageSize,username);
//        res.put("data",data);
//        res.put("count",count);
//        return res;
//    }
    //mybitis-plus方式
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam (defaultValue = "")String search){
        //若没有传值，则赋默认值，防止将所有数据筛选出来
        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper queryWrapper=new QueryWrapper<User>();
//        queryWrapper.like("username",search);
//        queryWrapper.or();
        queryWrapper.like("username",search);
        queryWrapper.or();
        queryWrapper.like("phone",search);
        queryWrapper.or();
        queryWrapper.like("campus",search);
        queryWrapper.or();
        queryWrapper.like("area",search);
        queryWrapper.or();
        queryWrapper.like("building",search);
        queryWrapper.or();
        queryWrapper.like("dormitory",search);
        queryWrapper.orderByDesc("id");
        //后台获取用户登录信息
        User currentUser= TokenUtils.getUserInfo();
        System.out.println("=========================当前用户信息:"+currentUser.getNickname());
        return userService.page(page,queryWrapper);
    }

    /*
    * 导出功能，导出当前页面中load的对象
    * */
    @GetMapping("/export")
    public void export(HttpServletResponse response)throws Exception{
        //从数据库查出数据
        List<User>list = userService.list();
        System.out.println(list.get(0).getAvatarUrl());
        //创建工具类writer，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("campus","校区");
        writer.addHeaderAlias("area","片区");
        writer.addHeaderAlias("building","楼幢号");
        writer.addHeaderAlias("dormitory","宿舍号");
        writer.addHeaderAlias("createTime","创建时间");
        writer.addHeaderAlias("avatarUrl","头像");
        //将list对象一次性写入到excel中，使用默认样式，强制输出标题
        writer.write(list,true);
        //设置浏览器响应的格式固定写法
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        Date nowtime=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String nowtimstr=sdf.format(nowtime);//Date类型转换为字符串
        String fileName = URLEncoder.encode("用户信息"+nowtimstr,"UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    /*
    *
    * excel导入接口
    * @prama
    * @throws Exception
    * */
    @PostMapping("/import")
    public boolean imp(MultipartFile file)throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list=reader.readAll(User.class);
        //System.out.println(list);
        return userService.saveBatch(list);
    }


    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam String username) {
        return Result.success(userService.getUserInfoDtoInfo(username));
    }

    @PostMapping("/upUserInfo")
    public Result upUserInfo(@RequestBody UserInfoDto user) {
        return Result.success(userService.upUserInfo(user));
    }
    @PostMapping("/changepw")
    public Result changepw(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String newpassword){
        return Result.success(userService.changepw(username,password,newpassword));
    }
    //没有任何用处，仅作为token校验
    @PostMapping("/token")
    public Result token(){return Result.success();}







}
