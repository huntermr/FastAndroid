package com.hunter.fastandroid.base;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;

/**
 * 公共响应参数
 * 
 * @author Ht
 * 
 */
public abstract class BaseResponse {
	private int code;
	private String msg;
	private String data;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 解析单条数据
	 * 
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 *             参数异常(Response中data为空)
	 * @throws JsonSyntaxException
	 *             Json解析异常
	 */
	public abstract <T> T getBean(Class<T> clazz)
			throws IllegalArgumentException, JsonSyntaxException;

	/**
	 * 解析数据列表
	 * 
	 * @param typeOfT
	 * @return
	 * @throws IllegalArgumentException
	 *             参数异常(Response中data为空)
	 * @throws JsonSyntaxException
	 *             Json解析异常
	 */
	public abstract <T> T getBeanList(Type typeOfT)
			throws IllegalArgumentException, JsonSyntaxException;

}
