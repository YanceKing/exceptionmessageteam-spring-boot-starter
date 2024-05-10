package com.yance.pojos.dingding;

import com.yance.properties.enums.DingdingTextType;

public class DingDingTextNotice extends DingDingNotice {

	private DingDingText text;

	public DingDingTextNotice(String msg, String[] phones) {
		super(new DingDingAt(phones), DingdingTextType.TEXT.getMsgType());
		text = new DingDingText(msg);
	}

	public DingDingText getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(DingDingText text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "DingDingTextNotice [text=" + text + ", at=" + at + ", msgtype=" + msgtype + "]";
	}

}
