package org.daelly.oj.utils;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int start = 0;
	
	private int pageSize = 15;
	
	private int count = 0;
	
	private int pageNo = 1;
	
	/**
	 * 状态：0位正常
	 * 非0：异常
	 */
	private String state = "0";
	
	private String msg = "OK";
	
	private List<T> datas;
	
	public Page(){}

	public Page(int start, int pageSize, int count, List<T> datas) {
		super();
		this.start = start;
		this.pageSize = pageSize==0?15:pageSize;
		this.count = count;
		this.datas = datas;
		this.pageNo = this.start / this.pageSize + 1;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize==0?15:pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

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

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
