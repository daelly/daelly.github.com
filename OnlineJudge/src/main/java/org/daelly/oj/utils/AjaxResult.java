package org.daelly.oj.utils;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class AjaxResult implements Serializable {

	private static final long serialVersionUID = 5648832072642792919L;
	
	private String state = "0";
	
	private String msg = "OK";
	
	private Object result = null;
	
	private List<Object> list = null;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}
	
	public AjaxResult(){}

	public AjaxResult(Object result) {
		super();
		this.result = result;
	}
	
	public AjaxResult error(String msg){
		this.setState("1");
		this.setMsg(msg);
		return this;
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
