package com.yance.notice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yance.httpclient.DingdingHttpClient;
import com.yance.pojos.PromethuesNotice;
import com.yance.pojos.dingding.DingDingNotice;
import com.yance.pojos.dingding.DingDingResult;
import com.yance.properties.enums.DingdingTextType;
import com.yance.properties.notice.DingDingNoticeProperty;
import com.yance.text.NoticeTextResolver;

public class DingDingNoticeSendComponent implements INoticeSendComponent {

	private final DingdingHttpClient httpClient;

	private final DingDingNoticeProperty dingDingNoticeProperty;

	private final Log logger = LogFactory.getLog(DingDingNoticeSendComponent.class);

	private final DingdingTextType dingdingTextType;

	private static final String NAME = "dingding";

	public DingDingNoticeSendComponent(DingdingHttpClient httpClient, DingDingNoticeProperty dingDingNoticeProperty,
			DingdingTextType dingdingTextType) {
		this.httpClient = httpClient;
		this.dingDingNoticeProperty = dingDingNoticeProperty;
		this.dingdingTextType = dingdingTextType;
	}

	/**
	 * @return the httpClient
	 */
	public DingdingHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	public void send(PromethuesNotice exceptionNotice, NoticeTextResolver noticeTextResolver) {
		String noticeText = noticeTextResolver.resolve(exceptionNotice);
		DingDingNotice dingDingNotice = dingDingNoticeProperty.generateDingdingNotice(noticeText,
				exceptionNotice.getTitle(), dingdingTextType);
		DingDingResult result = httpClient.doSend(dingDingNotice, dingDingNoticeProperty);
		if (logger.isTraceEnabled())
			logger.trace(result);
	}

	@Override
	public String name() {
		return NAME;
	}

}
