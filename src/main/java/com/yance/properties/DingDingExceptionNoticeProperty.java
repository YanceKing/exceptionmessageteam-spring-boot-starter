package com.yance.properties;

import java.util.Arrays;

/**
 * 异常通知 钉钉Property
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class DingDingExceptionNoticeProperty {

	/**
	 * 电话信息
	 */
	private String[] phoneNum;

	/**
	 * 钉钉机器人web钩子
	 */
	private String webHook;

	/**
	 * @return the phoneNum
	 */
	public String[] getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String[] phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the webHook
	 */
	public String getWebHook() {
		return webHook;
	}

	/**
	 * @param webHook the webHook to set
	 */
	public void setWebHook(String webHook) {
		this.webHook = webHook;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DingDingExceptionNoticeProperty [phoneNum=" + Arrays.toString(phoneNum) + ", webHook=" + webHook + "]";
	}

}
