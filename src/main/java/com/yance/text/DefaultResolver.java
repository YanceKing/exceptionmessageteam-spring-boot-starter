package com.yance.text;

import com.yance.notice.INoticeSendComponent;
import com.yance.pojos.PromethuesNotice;

public class DefaultResolver implements NoticeTextResolver {

	@Override
	public String resolve(PromethuesNotice notice) {
		return notice.toString();
	}

	@Override
	public boolean support(Class<? extends PromethuesNotice> clazz, INoticeSendComponent noticeSendComponent) {
		return true;
	}

}
