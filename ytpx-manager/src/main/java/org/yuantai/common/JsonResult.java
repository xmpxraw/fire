package org.yuantai.common;

import java.io.Serializable;

import org.yuantai.common.util.JsonUtil;

public class JsonResult implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
	private String msg;
	private Object data;
	
	public JsonResult() {}
	public JsonResult(int code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	public JsonResult(int code,String msg,Object data) {
		this.code=code;
		this.msg=msg;
		this.data=data;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJson() {
		return JsonUtil.toJson(this);
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("{").append(code).append(",").append(msg).append("}");
		return sb.toString();
	}
}
