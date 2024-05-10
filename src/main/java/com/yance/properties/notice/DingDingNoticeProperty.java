package com.yance.properties.notice;

import com.yance.pojos.dingding.DingDingMarkdownNotice;
import com.yance.pojos.dingding.DingDingNotice;
import com.yance.pojos.dingding.DingDingTextNotice;
import com.yance.properties.enums.DingdingTextType;

public class DingDingNoticeProperty {

	/**
	 * 电话信息
	 */
	private String[] phoneNum;

	/**
	 * 钉钉机器人的accessToken
	 */
	private String accessToken;

	/**
	 * 是否开启验签, 默认为true
	 */
	private boolean enableSignatureCheck = true;

	/**
	 * 验签秘钥
	 */
	private String signSecret;

	public String[] getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String[] phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isEnableSignatureCheck() {
		return enableSignatureCheck;
	}

	public void setEnableSignatureCheck(boolean enableSignatureCheck) {
		this.enableSignatureCheck = enableSignatureCheck;
	}

	public String getSignSecret() {
		return signSecret;
	}

	public void setSignSecret(String signSecret) {
		this.signSecret = signSecret;
	}

	public DingDingNotice generateDingdingNotice(String msg, String title, DingdingTextType dingdingTextType) {
		switch (dingdingTextType) {
		case MARKDOWN:
			return new DingDingMarkdownNotice(msg, title, phoneNum);
		case TEXT:
			return new DingDingTextNotice(msg, phoneNum);
		}
		// never happen;
		return null;
	}
}
