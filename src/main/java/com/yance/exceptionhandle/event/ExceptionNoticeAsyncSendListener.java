package com.yance.exceptionhandle.event;

import com.yance.common.abstracts.AbstractNoticeSendListener;
import com.yance.exceptionhandle.interfaces.NoticeStatisticsRepository;
import com.yance.notice.NoticeComponentFactory;
import com.yance.properties.frequency.NoticeFrequencyStrategy;
import com.yance.text.NoticeTextResolverProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Executor;

import static jakarta.mail.Transport.send;

public class ExceptionNoticeAsyncSendListener extends AbstractNoticeSendListener {

	private static final Log logger = LogFactory.getLog(ExceptionNoticeAsyncSendListener.class);

	private final Executor executor;

	/**
	 * @param noticeFrequencyStrategy
	 * @param exceptionNoticeStatisticsRepository
	 * @param resolverProvider
	 * @param noticeComponentFactory
	 * @param resolverName
	 * @param executor
	 */
	public ExceptionNoticeAsyncSendListener(NoticeFrequencyStrategy noticeFrequencyStrategy,
											NoticeStatisticsRepository exceptionNoticeStatisticsRepository, NoticeTextResolverProvider resolverProvider,
											NoticeComponentFactory noticeComponentFactory, String resolverName, Executor executor) {
		super(noticeFrequencyStrategy, exceptionNoticeStatisticsRepository, resolverProvider, noticeComponentFactory);
		this.executor = executor;
	}

	@Override
	public void onApplicationEvent(ExceptionNoticeEvent event) {
		logger.debug("异步发送消息");
		executor.execute(() -> send(event.getBlameFor(), event.getExceptionNotice()));
	}

}
