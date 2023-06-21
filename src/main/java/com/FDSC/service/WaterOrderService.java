package com.FDSC.service;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.FDSC.entity.WaterOrder;
import com.FDSC.mapper.WaterOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WaterOrderService extends ServiceImpl<WaterOrderMapper, WaterOrder> {
    @Autowired
    private WaterOrderMapper waterOrderMapper;
    //分页查询+筛选
    public Map<String,Object> getWaterOrderPage(Integer pageNum, Integer pageSize, String search, Integer selectCode,String username){
        System.out.println("============================selectCode"+selectCode);
        //获取当前日期
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today=sdf.format(date);

        pageNum=(pageNum-1)*pageSize;
        List<WaterOrder> data=waterOrderMapper.changePage(pageNum,pageSize,search,selectCode,today,username);
        Integer count=waterOrderMapper.changePageCount(pageNum,pageSize,search,selectCode,today,username);
        Map<String,Object>res=new HashMap<>();
        res.put("records",data);
        res.put("total",count);
        return res;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<WaterOrder> entityList) {
        return super.saveOrUpdateBatch(entityList);
    }
    //导出数据
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查出数据
        List<WaterOrder> list = waterOrderMapper.getList();
        //创建工具类writer，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("orderId", "系统id字段，导入时去除该列");
        writer.addHeaderAlias("orderUsername", "订水者用户名（学号）");
        writer.addHeaderAlias("orderCommodityName", "商品名称");
        writer.addHeaderAlias("orderCommodityNumber", "商品数量");
        writer.addHeaderAlias("orderPrice", "订单价格");
        writer.addHeaderAlias("orderIsComplete", "订单状态");
        writer.addHeaderAlias("orderCampus", "校区");
        writer.addHeaderAlias("orderArea", "片区");
        writer.addHeaderAlias("orderBuilding", "楼幢号");
        writer.addHeaderAlias("orderDormitory", "宿舍号");
        writer.addHeaderAlias("orderBuyTime", "订水时间");
        writer.addHeaderAlias("orderCreateTime", "系统时间字段，导入时去除该列");
        //将list对象一次性写入到excel中，使用默认样式，强制输出标题
        writer.write(list, true);
        //设置浏览器响应的格式固定写法
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        Date nowtime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowtimstr = sdf.format(nowtime);//Date类型转换为字符串
        String fileName = URLEncoder.encode("订单信息" + nowtimstr, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    public boolean upWaterOrder(String commodityname,String commoditynum,String username,Double sumprice,
                                String campus,String area,String building,String dormitory) {
        String namestr = commodityname.substring(0,commodityname.length()-1);
        String numstr = commoditynum.substring(0,commoditynum.length()-1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String buytime = sdf.format(new Date());
        return waterOrderMapper.upWaterOrder(namestr,username,numstr,0,buytime,sumprice,campus,area,building,dormitory);
    }

    //更新单个订单状态
    public boolean saveOrder(WaterOrder waterOrder) {
        return updateById(waterOrder);
    }

    //批量改变订单状态
    public boolean changeStatusBash(List<Integer> ids) {
       return waterOrderMapper.changeStatusBash(ids);
    }
}
