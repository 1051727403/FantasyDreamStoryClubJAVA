package com.FDSC.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.FDSC.common.Constants;
import com.FDSC.common.Result;
import com.FDSC.entity.WaterOrder;
import com.FDSC.exception.ServiceException;
import com.FDSC.service.WaterOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/water/order")
public class WaterOrderController {

    @Autowired
    private WaterOrderService waterOrderService;


    /**
     * 分页查询
     *
     * @param pageNum    页号
     * @param pageSize   每一页的大小
     * @param search     模糊查询
     * @param selectCode 筛选查询 0全部，1今日订单，2已完成 3未完成
     * @return 一个list表
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam (defaultValue = "")String search,
                           @RequestParam (defaultValue = "0")Integer selectCode,
                           @RequestParam (defaultValue = "" )String username){
        return Result.success(waterOrderService.getWaterOrderPage(pageNum,pageSize,search,selectCode,username));
    }

    @PostMapping("/enter")
    public Result loadWaterOrder(
            @RequestParam String commoditynames, @RequestParam String commoditynum,
            @RequestParam String username, @RequestParam Double sumprice,
            @RequestParam String campus,@RequestParam String area,
            @RequestParam String building,@RequestParam String dormitory) {
        return Result.success(waterOrderService.upWaterOrder(commoditynames,commoditynum,username,sumprice,campus,area,building,dormitory));
    }

//    @PostMapping("/enter")
//    public Result loadWaterOrder(@RequestParam Double sumprice) {
//        return Result.success(waterOrderService.upWaterOrder("commoditynames","commoditynum","username",sumprice,"buytime"));
//    }

    /**
     * 导出商品功能
     * @param response HttpServletResponse类型
     * @return 无
     * */
    @GetMapping("/export")
    public Result export(HttpServletResponse response)throws Exception{
        try {
            waterOrderService.export(response);
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
        List<WaterOrder> list=reader.readAll(WaterOrder.class);
        //调整时间格式
        for(int i=0;i<list.size();i++){
            list.get(i).setOrderBuyTime(list.get(i).getOrderBuyTime().substring(0,10));
        }
        //System.out.println(list);
        try {
            waterOrderService.saveBatch(list);
        }catch(ServiceException e){
            throw new ServiceException(Constants.CODE_500,"导入失败！");
        }
        return Result.success();
    }

    /**
     * 根据id删除
     * @param id 按照前端传来的id进行删除
     * */
    @DeleteMapping("/delById")
    public Result delById(@RequestParam Integer id) throws ServiceException{
        return Result.success(waterOrderService.removeById(id));
    }

    /**
     * 批量商品删除操作
     * @param ids 多个商品的id号list类型
     * @return 返回Result类型数据
     * */
    @PostMapping("/delBashById")
    public Result delBashById(@RequestBody List<Integer> ids) throws ServiceException{
        return Result.success(waterOrderService.removeByIds(ids));
    }


    /**
     * 更新订单状态
     * @param waterOrder 订单类
     * */
    @PostMapping("/save")
    public Result save(@RequestBody WaterOrder waterOrder){
        try {
            waterOrderService.saveOrder(waterOrder);
        }catch(ServiceException se) {
            return Result.error(Constants.CODE_600,"更新订单失败！");
        }
        return Result.success();
    }
    @PostMapping("/changeStatusBash")
    public Result changeStatusBash(@RequestBody List<Integer>ids){
        try {
            waterOrderService.changeStatusBash(ids);
        }catch (ServiceException se){
            return Result.error(Constants.CODE_500,"改变订单状态失败！");
        }

        return Result.success();
    }


    //没有任何用处，仅作为token校验
    @PostMapping("/token")
    public Result token(){
        return Result.success();
    }

}
