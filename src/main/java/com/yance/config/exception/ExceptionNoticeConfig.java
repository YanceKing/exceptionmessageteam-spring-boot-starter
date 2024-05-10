package com.yance.config.exception;

import com.yance.config.annos.ConditionalOnExceptionNotice;
import com.yance.exceptionhandle.ExceptionHandler;
import com.yance.properties.PrometheusProperties;
import com.yance.properties.exception.ExceptionNoticeProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExceptionNotice
@EnableConfigurationProperties({ ExceptionNoticeProperties.class })
public class ExceptionNoticeConfig {

	private final Log logger = LogFactory.getLog(ExceptionNoticeConfig.class);

	@Bean
	public ExceptionHandler exceptionHandler(PrometheusProperties noticeProperties,
											 ExceptionNoticeProperties exceptionNoticeProperties, ApplicationEventPublisher applicationEventPublisher) {
		logger.debug("创建异常处理器");
		ExceptionHandler exceptionHandler = new ExceptionHandler(noticeProperties, exceptionNoticeProperties,
				applicationEventPublisher);
		return exceptionHandler;
	}
}
