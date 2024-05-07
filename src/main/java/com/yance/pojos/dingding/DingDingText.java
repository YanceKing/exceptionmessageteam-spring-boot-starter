package com.yance.pojos.dingding;

/**
 * 钉钉消息 content
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class DingDingText {

	private String content;

	public DingDingText(String content) {
		this.content = content;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
