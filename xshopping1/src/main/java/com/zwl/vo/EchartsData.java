package com.zwl.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *   Echarts图形需要的格式数据
 */
public class EchartsData implements Serializable {

    private Map title;      //标题
    private Map tooltip;    //提示
    private Map legend;     //图例布局
    private Map xAxis;
    private Map yAxis;
    private List<Series> series; //饼图中的数据

    public static class Series{

        private String name;
        private String type;
        private String radius;
        private Boolean showBackground;
        private List<Object> data;

        public Boolean getShowBackground() {
            return showBackground;
        }

        public void setShowBackground(Boolean showBackground) {
            this.showBackground = showBackground;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public List<Object> getData() {
            return data;
        }

        public void setData(List<Object> data) {
            this.data = data;
        }
    }

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
}
