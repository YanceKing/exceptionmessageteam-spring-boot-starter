package com.yance.config.exception;

import com.yance.common.abstracts.AbstractNoticeSendListener;
import com.yance.config.annos.ConditionalOnExceptionNotice;
import com.yance.exceptionhandle.event.ExceptionNoticeAsyncSendListener;
import com.yance.exceptionhandle.event.ExceptionNoticeSendListener;
import com.yance.exceptionhandle.interfaces.NoticeStatisticsRepository;
import com.yance.notice.NoticeComponentFactory;
import com.yance.properties.exception.ExceptionNoticeFrequencyStrategy;
import com.yance.text.NoticeTextResolverProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;

@Configuration
@ConditionalOnExceptionNotice
@EnableConfigurationProperties({ ExceptionNoticeFrequencyStrategy.class })
public class ExceptionNoticeSendConfig {

	private final Log logger = LogFactory.getLog(ExceptionNoticeSendConfig.class);

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "prometheus.exceptionnotice.enable-async", havingValue = "false", matchIfMissing = true)
	public AbstractNoticeSendListener exceptionNoticeSendListener(
			ExceptionNoticeFrequencyStrategy noticeFrequencyStrategy,
			NoticeStatisticsRepository exceptionNoticeStatisticsRepository, NoticeTextResolverProvider resolverProvider,
			NoticeComponentFactory noticeComponentFactory) {
		logger.debug("创建同步发送监听器");
		AbstractNoticeSendListener listener = new ExceptionNoticeSendListener(noticeFrequencyStrategy,
				exceptionNoticeStatisticsRepository, resolverProvider, noticeComponentFactory, "");
		return listener;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "prometheus.exceptionnotice.enable-async", havingValue = "true")
	public AbstractNoticeSendListener ExceptionNoticeAsyncSendListener(
			ExceptionNoticeFrequencyStrategy noticeFrequencyStrategy,
			NoticeStatisticsRepository exceptionNoticeStatisticsRepository, NoticeTextResolverProvider resolverProvider,
			NoticeComponentFactory noticeComponentFactory, AsyncTaskExecutor applicationTaskExecutor) {
		logger.debug("创建异步发送监听器");
		AbstractNoticeSendListener listener = new ExceptionNoticeAsyncSendListener(noticeFrequencyStrategy,
				exceptionNoticeStatisticsRepository, resolverProvider, noticeComponentFactory, null,
				applicationTaskExecutor);
		return listener;
	}
}
