package com.redsea.vo;

import com.jfinal.kit.JsonKit;

/**
 * 功能描述: 封装ajax返回
 * @author Rocky
 * email: 464193096@qq.com
 * site:http://www.hr-soft.cn/
 * date: 2015年7月31日 下午9:50:58
 */
public class AjaxResult {

	public AjaxResult() {
	}

	public AjaxResult(boolean succeed) {
		if (!succeed) {
			addError("操作失败！");
		}
	}

	// 标记成功失败，默认0：成功，1：失败、用于alert，2：失败、用于confirm
	private int code = 0;

	// 返回的中文消息
	private String message;

	// 成功时携带的数据
	private Object data;
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	// 校验错误
	public boolean hasError() {
		return this.code != 0;
	}

	// 添加错误，用于alertError
	public AjaxResult addError(String message) {
		this.message = message;
		this.code = 1;
		return this;
	}

	/**
	 * 用于Confirm的错误信息
	 * @param addConfirmError
	 * @return AjaxResult
	 */
	public AjaxResult addConfirmError(String message) {
		this.message = message;
		this.code = 2;
		return this;
	}
	
	/**
	 * 封装成功时的数据
	 * @param data
	 * @return AjaxResult
	 */
	public AjaxResult success(Object data) {
		this.data = data;
		return this;
	}
	
	/**
	 * 封装成功时的数据
	 * @param data
	 * @param message
	 * @return AjaxResult
	 */
	public AjaxResult success(Object data,String message) {
		this.data = data;
		this.message = message;
		return this;
	}
	
	@Override
	public String toString() {
		return JsonKit.toJson(this);
	}
}
