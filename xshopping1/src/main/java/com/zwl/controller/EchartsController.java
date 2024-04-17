package com.zwl.controller;

import cn.hutool.json.JSONObject;
import com.zwl.common.Result;
import com.zwl.service.CommentInfoService;
import com.zwl.service.OrderInfoService;
import com.zwl.service.UserInfoService;
import com.zwl.vo.EchartsData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * 后台统计相关的控制器
 */
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private CommentInfoService commentInfoService;

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 统计各种总数
     */
    @GetMapping("getTotal")
    public Result<Map<String,Object>> getTotal(){
        Map<String,Object> map = new HashMap<>();
        //获取用户总数
        map.put("totalUser", userInfoService.count());
        //获取评论总数
        map.put("totalComment", commentInfoService.count());
        //获取已完成的总交易额总数
        map.put("totalPrice", orderInfoService.totalPrice());
        //获取总销量
        map.put("totalShopping", orderInfoService.totalShopping());
        return Result.success(map);
    }

    /**
     * 统计分类总销售额
     */
    @GetMapping("/get/price")
    public Result<List<EchartsData>> getPriceEchartsData(){
        List<EchartsData> list = new ArrayList<>();
        List<Map<String,Object>> typePriceList = orderInfoService.getTypePrice();
        //格式转换
        Map<String, Double> typeMap = new HashMap<>();
        for (Map<String,Object> map: typePriceList){
            typeMap.put((String)map.get("name"),(Double) map.get("totalprice"));
        }
        //饼图
        getPieData("分类总销售额(已付款)", list, typeMap);
        //柱状图
        getBarData("分类总销售额(已付款)", list, typeMap);
        //折线图
        getLineData("分类总销售额(已付款)", list, typeMap);
        //散点图
        getScatterData("分类总销售额(已付款)", list, typeMap);
        return Result.success(list);
    }

    /**
     * 统计分类总销售量
     */
    @GetMapping("/get/shopping")
    public Result<List<EchartsData>> getShoppingEchartsData(){
        List<EchartsData> list = new ArrayList<>();
        List<Map<String,Object>> typeCountList = orderInfoService.getTypeCount();
        //格式转换
        Map<String, Double> typeMap = new HashMap<>();
        for (Map<String,Object> map: typeCountList){
            typeMap.put((String)map.get("name"), ((BigDecimal) map.get("count")).doubleValue());
        }
        //饼图
        getPieData("分类总销量(已付款)", list, typeMap);
        //柱状图
        getBarData("分类总销量(已付款)", list, typeMap);
        //折线图
        getLineData("分类总销量(已付款)", list, typeMap);
        //散点图
        getScatterData("分类总销量(已付款)", list, typeMap);
        return Result.success(list);
    }

    /**
     * 封装饼图
     * @param name    标题
     * @param pieList 封装完给前端显示的List
     * @param dataMap 传入的数据
     */

    private void getPieData(String name, List pieList, Map<String,Double> dataMap){
        EchartsData pieData = new EchartsData();

        //设置标题
        Map<String,String> titleMap = new HashMap<>();
        titleMap.put("text", name);
        titleMap.put("left", "center");
        pieData.setTitle(titleMap);

        //鼠标移动到对应的位置提示数据
        Map<String,Object> tooltip = new HashMap<>();
        tooltip.put("show", true);
        pieData.setTooltip(tooltip);

        //图例布局位置
        Map<String,Object> legendMap = new HashMap<>();
        legendMap.put("orient", "vertical");
        legendMap.put("left", "left");
        pieData.setLegend(legendMap);

        //饼图中的数据
        EchartsData.Series series = new EchartsData.Series();
        series.setName(name);
        series.setType("pie");

        List<String> radius = new ArrayList<>();
        radius.add("40%");
        radius.add("70%");
        series.setRadius(radius);

        series.setAvoidLabelOverlap(false);


        Map<String,Object> label = new HashMap<>();
        label.put("show", false);
        label.put("position", "center");
        series.setLabel(label);

        Map<String, Object> emphasis = new HashMap<>();
        Map<String, Object> emphasisLabel = new HashMap<>();
        Map<String, Object> emphasisItemStyle = new HashMap<>();
        emphasisLabel.put("show", true);
        emphasisLabel.put("fontSize", 35);
        emphasisLabel.put("fontWeight", "bold");
        emphasisItemStyle.put("shadowBlur", 10);
        emphasisItemStyle.put("shadowOffsetX", 0);
        emphasisItemStyle.put("shadowColor", "rgba(252, 251, 3, 1)");
        emphasis.put("label", emphasisLabel);
        emphasis.put("itemStyle", emphasisItemStyle);
        series.setEmphasis(emphasis);

        Map<String,Object> labelLine = new HashMap<>();
        labelLine.put("show", true);
        series.setLabelLine(labelLine);

        List<Object> objects = new ArrayList<>();
        for(String key: dataMap.keySet()){
            objects.add(new JSONObject().putOpt("name",key).putOpt("value",dataMap.get(key)));
        }
        series.setData(objects);
        pieData.setSeries(Collections.singletonList(series));
        pieList.add(pieData);
    }


    /**
     * 封装柱状图
     * @param name    标题
     * @param barList 封装完给前端显示的List
     * @param dataMap 传入的数据
     */
    private void getBarData(String name, List barList, Map<String,Double> dataMap){
        EchartsData barData = new EchartsData();

        //柱状图中的数据
        EchartsData.Series series = new EchartsData.Series();
        series.setName(name);
        series.setType("bar");
        series.setShowBackground(true);

        //图例布局位置
        Map<String,Object> legendMap = new HashMap<>();
        legendMap.put("left", "center");
        legendMap.put("top", "5%");
        barData.setLegend(legendMap);

        List<Object> objects = new ArrayList<>();
        List<Object> xAxisObjs = new ArrayList<>();
        for(String key: dataMap.keySet()){
            objects.add(dataMap.get(key));
            xAxisObjs.add(key);
        }

        //设置标题
        Map<String,String> titleMap = new HashMap<>();
        titleMap.put("text", name);
        titleMap.put("left", "left");
        barData.setTitle(titleMap);

        //鼠标移动到对应的位置提示数据
        Map<String,Object> tooltip = new HashMap<>();
        tooltip.put("show", true);
        barData.setTooltip(tooltip);

        Map<String, Object> emphasis = new HashMap<>();
        Map<String, Object> emphasisLabel = new HashMap<>();
        Map<String, Object> emphasisItemStyle = new HashMap<>();
        emphasisLabel.put("show", true);
        emphasisLabel.put("fontSize", 20);
        emphasisLabel.put("fontWeight", "bold");
        emphasisItemStyle.put("shadowBlur", 10);
        emphasisItemStyle.put("shadowOffsetX", 0);
        emphasisItemStyle.put("shadowColor", "rgba(252, 251, 3, 1)");
        emphasis.put("label", emphasisLabel);
        emphasis.put("itemStyle", emphasisItemStyle);
        series.setEmphasis(emphasis);

        //x轴数据
        Map<String,Object> xAxisMap = new HashMap<>();
        xAxisMap.put("data", xAxisObjs);

        barData.setxAxis(xAxisMap);
        barData.setyAxis(new HashMap());

        series.setData(objects);
        barData.setSeries(Collections.singletonList(series));
        barList.add(barData);
    }

    /**
     * 封装折线图
     * @param name    标题
     * @param lineList 封装完给前端显示的List
     * @param dataMap 传入的数据
     */
    private void getLineData(String name, List lineList, Map<String,Double> dataMap){
        EchartsData lineData = new EchartsData();

        //折线图中的数据
        EchartsData.Series series = new EchartsData.Series();
        series.setName(name);
        series.setType("line");
        series.setAreaStyle(true);
        series.setShowBackground(true);
        series.setBoundaryGap(false);
        series.setSmooth(true);

        List<Object> objects = new ArrayList<>();
        List<Object> xAxisObjs = new ArrayList<>();
        for(String key: dataMap.keySet()){
            objects.add(dataMap.get(key));
            xAxisObjs.add(key);
        }

        //设置标题
        Map<String,String> titleMap = new HashMap<>();
        titleMap.put("text", name);
        titleMap.put("left", "center");
        lineData.setTitle(titleMap);

        Map<String, Object> emphasis = new HashMap<>();
        Map<String, Object> emphasisLabel = new HashMap<>();
        Map<String, Object> emphasisItemStyle = new HashMap<>();
        emphasisLabel.put("show", true);
        emphasisLabel.put("fontSize", 20);
        emphasisLabel.put("fontWeight", "bold");
        emphasisItemStyle.put("shadowBlur", 10);
        emphasisItemStyle.put("shadowOffsetX", 0);
        emphasisItemStyle.put("shadowColor", "rgba(252, 251, 3, 1)");
        emphasis.put("label", emphasisLabel);
        emphasis.put("itemStyle", emphasisItemStyle);
        series.setEmphasis(emphasis);

        //鼠标移动到对应的位置提示数据
        Map<String,Object> tooltip = new HashMap<>();
        tooltip.put("show", true);
        lineData.setTooltip(tooltip);

        //x轴数据
        Map<String,Object> xAxisMap = new HashMap<>();
        xAxisMap.put("data", xAxisObjs);
        xAxisMap.put("boundaryGap",false);
        lineData.setxAxis(xAxisMap);
        lineData.setyAxis(new HashMap());
        series.setData(objects);


        lineData.setSeries(Collections.singletonList(series));
        lineList.add(lineData);
    }


    /**
     * 封装散点图
     * @param name    标题
     * @param scatterList 封装完给前端显示的List
     * @param dataMap 传入的数据
     */
    private void getScatterData(String name, List scatterList, Map<String,Double> dataMap){
        EchartsData scatterData = new EchartsData();

        //柱状图中的数据
        EchartsData.Series series = new EchartsData.Series();
        series.setName(name);
        series.setType("scatter");
        series.setSymbolSize(10);
        series.setShowBackground(true);

        //图例布局位置
        Map<String,Object> legendMap = new HashMap<>();
        legendMap.put("left", "center");
        legendMap.put("top", "5%");
        scatterData.setLegend(legendMap);

        List<Object> objects = new ArrayList<>();
        List<Object> xAxisObjs = new ArrayList<>();
        for(String key: dataMap.keySet()){
            objects.add(dataMap.get(key));
            xAxisObjs.add(key);
        }

        //设置标题
        Map<String,String> titleMap = new HashMap<>();
        titleMap.put("text", name);
        titleMap.put("left", "left");
        scatterData.setTitle(titleMap);

        //鼠标移动到对应的位置提示数据
        Map<String,Object> tooltip = new HashMap<>();
        tooltip.put("show", true);
        scatterData.setTooltip(tooltip);

        Map<String, Object> emphasis = new HashMap<>();
        Map<String, Object> emphasisLabel = new HashMap<>();
        Map<String, Object> emphasisItemStyle = new HashMap<>();
        emphasisLabel.put("show", true);
        emphasisLabel.put("fontSize", 20);
        emphasisLabel.put("fontWeight", "bold");
        emphasisItemStyle.put("shadowBlur", 10);
        emphasisItemStyle.put("shadowOffsetX", 0);
        emphasisItemStyle.put("shadowColor", "rgba(252, 251, 3, 1)");
        emphasis.put("label", emphasisLabel);
        emphasis.put("itemStyle", emphasisItemStyle);
        series.setEmphasis(emphasis);

        //x轴数据
        Map<String,Object> xAxisMap = new HashMap<>();
        xAxisMap.put("data", xAxisObjs);

        scatterData.setxAxis(xAxisMap);
        scatterData.setyAxis(new HashMap());

        series.setData(objects);
        scatterData.setSeries(Collections.singletonList(series));
        scatterList.add(scatterData);
    }
}
