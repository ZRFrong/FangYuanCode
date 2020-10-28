package com.ruoyi.fangyuanapi.wechat;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信证书平台列表
 */
public class Certificates {

	private List<Certificate> data = new ArrayList<Certificate>();

	@Override
	public String toString() {
		return "Certificates [data=" + data + "]";
	}

	public List<Certificate> getData() {
		return data;
	}

	public void setData(List<Certificate> data) {
		this.data = data;
	}
	
}
