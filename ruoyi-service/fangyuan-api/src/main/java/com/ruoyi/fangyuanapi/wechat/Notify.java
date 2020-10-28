package com.ruoyi.fangyuanapi.wechat;

public class Notify {

	private String id;
	private String create_time;
	private String resource_type;
	private String event_type;
	private String summary;
	private NotifyResource resource;
	
	@Override
	public String toString() {
		return "Notify [id=" + id + ", create_time=" + create_time + ", resource_type=" + resource_type + ", event_type=" + event_type + ", summary=" + summary + ", resource=" + resource + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public NotifyResource getResource() {
		return resource;
	}
	public void setResource(NotifyResource resource) {
		this.resource = resource;
	}
	
}
