package com.FDSC.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.FDSC.common.Result;
import com.FDSC.controller.dto.UserInfoDto;
import com.FDSC.entity.ElectricityPayment;
import com.FDSC.entity.User;
import com.FDSC.service.ElectricityPaymentService;
import com.FDSC.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.FDSC.controller.dto.ElectricityPaymentDto;
import com.FDSC.controller.dto.ElectricityPaymentUsernameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName:ElectricityPaymentController
 * Package: com.lms.logistics_management_system.controller
 * Description:
 * Author maple
 * Create 2023-02-13
 * Version: v1.0
 */
// 电费支付
@RequestMapping("/epayment")
@RestController
@CrossOrigin
@Slf4j
public class ElectricityPaymentController {

    @Resource
    private ElectricityPaymentService electricityPaymentService;
    @Resource
    private UserService userService;

    @GetMapping("/info")
    public Result getAllInfo(){
        List<ElectricityPaymentDto> allInfo = electricityPaymentService.getAllInfo();
        return Result.success(allInfo);
    }

    @GetMapping("/page")
    public Result getPageInfo(Integer pageNum, Integer pageSize, String search, String state){
        return Result.success(electricityPaymentService.getPageElectricityPaymentDto(pageNum, pageSize, search, state));
    }

    @GetMapping("/pay")
    public Result getPageInfo(Integer pageNum, Integer pageSize, String campus,String area,String building, String roomId, String state){
        return Result.success(electricityPaymentService.getPageElectricityPaymentDto(pageNum, pageSize, campus, area, building, roomId, state));
    }

    @GetMapping("/history")
    public Result getPageInfo(Integer pageNum, Integer pageSize, Integer uid, String state) {
        return Result.success(electricityPaymentService.getPageElectricityPaymentDto(pageNum, pageSize, uid, state));
    }

    // state是筛选条件
    // search是模糊查询的内容
    @GetMapping("/export")
    public void export(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam String search,
                       @RequestParam String state,
                       HttpServletResponse response)throws Exception{
        Integer count = (int)electricityPaymentService.count();
        //从数据库查出数据
        List<ElectricityPaymentDto> list = electricityPaymentService.getPageElectricityPaymentDto(0, count, search, state).getRecords();
        //创建工具类writer，写出到浏览器
        List<ElectricityPaymentUsernameDto> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ElectricityPaymentUsernameDto e = new ElectricityPaymentUsernameDto();
            BeanUtils.copyProperties(list.get(i), e, "user");
            if(list.get(i).getUser() != null)
            {
                log.info(list.get(i).getUser().toString());
                e.setUsername(list.get(i).getUser().getUsername());
                e.setNickname(list.get(i).getUser().getNickname());
            }
            res.add(e);
        }
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名

        writer.addHeaderAlias("id","账单编号");
        writer.addHeaderAlias("uid","学生主键");
        writer.addHeaderAlias("username","缴费学生学号");
        writer.addHeaderAlias("nickname","缴费学生姓名");
        writer.addHeaderAlias("campus","校区");
        writer.addHeaderAlias("status","是否缴费");
        writer.addHeaderAlias("area","区域");
        writer.addHeaderAlias("building","楼栋");
        writer.addHeaderAlias("roomId","宿舍楼");
        writer.addHeaderAlias("money","应该缴费金额");
        writer.addHeaderAlias("createTime","创建时间");
        //将list对象一次性写入到excel中，使用默认样式，强制输出标题
        writer.write(res,true);
        //设置浏览器响应的格式固定写法
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        Date nowtime=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String nowtimstr=sdf.format(nowtime);//Date类型转换为字符串
        String name = "缴费信息";
        if(search != null && search != "") name += '_' + search;
        if(state != null && state != "") name += '_' + state;
        String fileName = URLEncoder.encode(name + '_' + nowtimstr,"UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public boolean imp(MultipartFile file)throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<ElectricityPayment> list=reader.readAll(ElectricityPayment.class);
        return electricityPaymentService.saveBatch(list);
    }
    @PostMapping
    public Result saveOrEpayment(@RequestBody ElectricityPaymentUsernameDto electricityPayment){
        String username = electricityPayment.getUsername();
        log.info(electricityPayment.toString());
        if(username != null && username != "")
        {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            UserInfoDto user = userService.getUserInfoDtoInfo(username);
            if(user == null)
            {
                return Result.error("500", "学号不存在");
            }
            electricityPayment.setUid(user.getId());
            if( user.getBuilding() != null && !user.getBuilding().equals(electricityPayment.getBuilding())
            || user.getCampus() != null &&!user.getCampus().equals(electricityPayment.getCampus())
            || user.getArea() != null && !user.getArea().equals(electricityPayment.getArea())
            || user.getDormitory() != null && !user.getDormitory().equals(electricityPayment.getRoomId()))
            {
                return Result.error("500", "与该学生的所住宿舍矛盾");
            }
        }
        boolean b = electricityPaymentService.saveOrUpdate(electricityPayment);
        return b ? Result.success("保存成功"): Result.error();
    }

}
