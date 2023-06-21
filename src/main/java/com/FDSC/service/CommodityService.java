package com.FDSC.service;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.FDSC.common.Result;
import com.FDSC.entity.Commodity;
import com.FDSC.mapper.CommodityMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityService extends ServiceImpl<CommodityMapper, Commodity> {

    @Autowired
    private CommodityMapper commodityMapper;

    //返回商品数量
    public Integer selectCount(){
        return commodityMapper.selectCount();
    }

    //保存商品信息或更新
    public boolean saveCommodity(Commodity commodity){
        if(commodity.getCommodityId()==null){
            //没有id代表新增
            return save(commodity);
        }else{
            //有id代表更新
            return updateById(commodity);
        }
    }


    public Result delCommodity(Integer id) {
        boolean success=commodityMapper.delCommodity(id);
        //System.out.println("success:"+success);
        if(success){
            return Result.success();
        }else{
            return Result.error();
        }
    }


    public Result delBashCommodity(List<Integer> ids) {
        boolean success=commodityMapper.delBashCommodity(ids);
        //System.out.println("success:"+success);
        if(success){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /*
    *   导出
    *  */
    public void export(HttpServletResponse response)throws Exception {
        //从数据库查出数据
        List<Commodity>list = commodityMapper.getList();
        //创建工具类writer，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题名
        writer.addHeaderAlias("commodityName","商品名称");
        writer.addHeaderAlias("commodityPrice","商品价格");
        writer.addHeaderAlias("commoditySupplier","商品供货商名称");
        writer.addHeaderAlias("commodityAvatar","商品图片");
        writer.addHeaderAlias("commodityCreateTime","创建时间");
        //将list对象一次性写入到excel中，使用默认样式，强制输出标题
        writer.write(list,true);
        //设置浏览器响应的格式固定写法
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        Date nowtime=new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String nowtimstr=sdf.format(nowtime);//Date类型转换为字符串
        String fileName = URLEncoder.encode("商品信息"+nowtimstr,"UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }

    //分页查询
    public Map<String,Object> getCommodityPage(Integer pageNum, Integer pageSize, String search) {
        pageNum=(pageNum-1)*pageSize;
        Map<String,Object> res=new HashMap<>();
        List<Commodity>data= commodityMapper.changePage(pageNum,pageSize,search);
        Integer count=commodityMapper.changePageCommodityCount(pageNum,pageSize,search);
        res.put("data",data);
        res.put("count",count);
        return res;
    }
}
