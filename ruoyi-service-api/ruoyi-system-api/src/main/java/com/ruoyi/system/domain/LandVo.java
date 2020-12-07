package com.ruoyi.system.domain;

import java.util.List;

public class LandVo {


    private String plotName;

    @Override
    public String toString() {
        return "LandVo{" +
                "plotName='" + plotName + '\'' +
                ", lands=" + lands +
                '}';
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public List<DbLand> getLands() {
        return lands;
    }

    public void setLands(List<DbLand> lands) {
        this.lands = lands;
    }

    private List<DbLand> lands;

}
