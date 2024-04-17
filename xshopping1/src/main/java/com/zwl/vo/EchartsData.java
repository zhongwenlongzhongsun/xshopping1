package com.zwl.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *   Echarts图形需要的格式数据
 */
@Data
public class EchartsData implements Serializable {

    private Map title;      //标题
    private Map tooltip;    //提示
    private Map legend;     //图例布局
    private Map xAxis;
    private Map yAxis;
    private List<Series> series; //饼图中的数据

    public Map getTitle() {
        return title;
    }

    public void setTitle(Map title) {
        this.title = title;
    }

    public Map getTooltip() {
        return tooltip;
    }

    public void setTooltip(Map tooltip) {
        this.tooltip = tooltip;
    }

    public Map getLegend() {
        return legend;
    }

    public void setLegend(Map legend) {
        this.legend = legend;
    }

    public Map getxAxis() {
        return xAxis;
    }

    public void setxAxis(Map xAxis) {
        this.xAxis = xAxis;
    }

    public Map getyAxis() {
        return yAxis;
    }

    public void setyAxis(Map yAxis) {
        this.yAxis = yAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    @Data
    public static class Series{

        private String name;
        private String type;
        private List<String> radius;
        private Boolean showBackground;
        private List<Object> data;
        private Object areaStyle;
        private Boolean boundaryGap;
        private Boolean smooth;
        private Boolean avoidLabelOverlap;
        private Integer symbolSize;
        private Map label;
        private Map labelLine;
        private Map emphasis;
        private Map emphasisLabel;
        private Map emphasisItemStyle;
        private Map itemStyle;

    }

}
