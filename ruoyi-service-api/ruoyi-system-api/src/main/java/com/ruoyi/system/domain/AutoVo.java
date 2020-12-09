package com.ruoyi.system.domain;

public class AutoVo {

    private  Double autoUp;

    @Override
    public String toString() {
        return "AutoVo{" +
                "autoUp=" + autoUp +
                ", autoDown=" + autoDown +
                '}';
    }

    public Double getAutoUp() {
        return autoUp;
    }

    public void setAutoUp(Double autoUp) {
        this.autoUp = autoUp;
    }

    public Double getAutoDown() {
        return autoDown;
    }

    public void setAutoDown(Double autoDown) {
        this.autoDown = autoDown;
    }

    private  Double autoDown

            ;
}
