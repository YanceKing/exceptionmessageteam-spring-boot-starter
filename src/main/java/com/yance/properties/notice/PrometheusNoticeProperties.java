package com.yance.properties.notice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import com.yance.properties.enums.DingdingTextType;
import com.yance.properties.enums.EmailTextType;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "prometheus.notice")
public class PrometheusNoticeProperties {

	/**
	 * 钉钉相关配置
	 */
	private Map<String, DingDingNoticeProperty> dingding = new HashMap<>();

	/**
	 * 电子邮件相关配置
	 */
	private Map<String, EmailNoticeProperty> email = new HashMap<>();

	/**
	 * 钉钉通知文本类型
	 */
	private DingdingTextType dingdingTextType = DingdingTextType.TEXT;

	/**
	 * 邮件的通知文本类型
	 */
	private EmailTextType emailTextType = EmailTextType.TEXT;

	/**
	 * 是否开启异步通知
	 */
	private boolean enableAsync = false;

	public Map<String, DingDingNoticeProperty> getDingding() {
		return dingding;
	}

	public void setDingding(Map<String, DingDingNoticeProperty> dingding) {
		this.dingding = dingding;
	}

	public Map<String, EmailNoticeProperty> getEmail() {
		return email;
	}

	public void setEmail(Map<String, EmailNoticeProperty> email) {
		this.email = email;
	}

	public DingdingTextType getDingdingTextType() {
		return dingdingTextType;
	}

	public void setDingdingTextType(DingdingTextType dingdingTextType) {
		this.dingdingTextType = dingdingTextType;
	}

	public EmailTextType getEmailTextType() {
		return emailTextType;
	}

	public void setEmailTextType(EmailTextType emailTextType) {
		this.emailTextType = emailTextType;
	}

	public boolean isEnableAsync() {
		return enableAsync;
	}

	public void setEnableAsync(boolean enableAsync) {
		this.enableAsync = enableAsync;
	}

}
