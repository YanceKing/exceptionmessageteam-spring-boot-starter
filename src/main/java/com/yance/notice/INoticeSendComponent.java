package com.yance.notice;

import com.yance.common.interfaces.Unique;
import com.yance.pojos.PromethuesNotice;
import com.yance.text.NoticeTextResolver;

public interface INoticeSendComponent extends Unique {

	void send(PromethuesNotice exceptionNotice, NoticeTextResolver noticeTextResolver);

}
