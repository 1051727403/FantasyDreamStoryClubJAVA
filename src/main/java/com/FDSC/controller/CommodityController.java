package com.FDSC.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.Commodity;
import com.FDSC.exception.ServiceException;
import com.FDSC.mapper.CommodityMapper;
import com.FDSC.service.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/water/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CommodityMapper commodityMapper;

    //分页查询手写
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam (defaultValue = "")String search){
        return Result.success(commodityService.getCommodityPage(pageNum,pageSize,search));
    }

    /**
     * 新增商品
     * @param commodity 商品类
     * @return 返回boolean类型 1成功 0失败
     * */
    @PostMapping("/addCommodity")
    public Result saveCommodity(@RequestBody Commodity commodity){
        if(commodityService.saveCommodity(commodity)){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_600,"保存商品失败！");
        }
    }

    /**
     * 单个商品删除操作
     * @param id 商品的id号
     * @return 返回Result类型数据
     * */
    @DeleteMapping("/delCommodity")
    public Result delCommodity(@RequestParam("id") Integer id){
        return commodityService.delCommodity(id);
    }

    /**
     * 批量商品删除操作
     * @param ids 多个商品的id号list类型
     * @return 返回Result类型数据
     * */
    @PostMapping("/delBashCommodity")
    public Result delBashCommodity(@RequestBody List<Integer> ids){
        return commodityService.delBashCommodity(ids);
    }


    /**
     * 导出商品功能
     * @param response HttpServletResponse类型
     * @return 无
     * */
    @GetMapping("/export")
    public Result export(HttpServletResponse response)throws Exception{
        try {
            commodityService.export(response);
        }catch(ServiceException serviceException){
            throw new ServiceException(Constants.CODE_600,"导出失败！");
        }
        return Result.success();
    }
    /**
     *
     * excel导入接口
     * @prama
     * @throws ServiceException
     * */
    @PostMapping("/import")
    public Result imp(MultipartFile file)throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Commodity> list=reader.readAll(Commodity.class);
        //System.out.println(list);
        try {
            commodityService.saveBatch(list);
        }catch(ServiceException e){
            throw new ServiceException(Constants.CODE_500,"导入失败！");
        }
        return Result.success();
    }
    //没有任何用处，仅作为token校验
    @PostMapping("/token")
    public boolean token(){
        return true;
    }

}
