package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DbAppUpdate {

    @ApiModelProperty(value = "是否更新")
    private String update;
    @ApiModelProperty(value = "版本号")
    private String new_version;
    @ApiModelProperty(value = "下载链接")
    private String apk_file_url;

    @ApiModelProperty(value = "更新日志")
    private String update_log;
    @ApiModelProperty(value = "文件大小")
    private String target_size;
    @ApiModelProperty(value = "md5")
    private String new_md5;
    @ApiModelProperty(value = "是否强制")
    private boolean constraint;

    @Override
    public String toString() {
        return "DbAppUpdate{" +
                "update='" + update + '\'' +
                ", new_version='" + new_version + '\'' +
                ", apk_file_url='" + apk_file_url + '\'' +
                ", update_log='" + update_log + '\'' +
                ", target_size='" + target_size + '\'' +
                ", new_md5='" + new_md5 + '\'' +
                ", constraint=" + constraint +
                '}';
    }

    public DbAppUpdate() {
    }

    public DbAppUpdate(String update, String new_version, String apk_file_url, String update_log, String target_size, String new_md5, boolean constraint) {

        this.update = update;
        this.new_version = new_version;
        this.apk_file_url = apk_file_url;
        this.update_log = update_log;
        this.target_size = target_size;
        this.new_md5 = new_md5;
        this.constraint = constraint;
    }

    public String getUpdate() {

        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getApk_file_url() {
        return apk_file_url;
    }

    public void setApk_file_url(String apk_file_url) {
        this.apk_file_url = apk_file_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public String getNew_md5() {
        return new_md5;
    }

    public void setNew_md5(String new_md5) {
        this.new_md5 = new_md5;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }


}
