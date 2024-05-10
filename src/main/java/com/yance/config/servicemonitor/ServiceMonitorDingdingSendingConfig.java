package com.yance.config.servicemonitor;

import com.yance.config.annos.ConditionalOnServiceMonitor;
import com.yance.config.notice.NoticeTextResolverCustomer;
import com.yance.properties.enums.DingdingTextType;
import com.yance.properties.notice.PrometheusNoticeProperties;
import com.yance.text.NoticeTextResolverProvider;
import com.yance.text.markdown.ServiceMonitorMarkdownResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@ConditionalOnServiceMonitor
public class ServiceMonitorDingdingSendingConfig implements NoticeTextResolverCustomer {

	private final Log logger = LogFactory.getLog(ServiceMonitorDingdingSendingConfig.class);

	@Autowired
	private PrometheusNoticeProperties prometheusNoticeProperties;

	@Override
	public void custom(NoticeTextResolverProvider resolverProvider) {
		logger.debug("创建钉钉markdown解析——服务异常通知");
		if (prometheusNoticeProperties.getDingdingTextType() == DingdingTextType.MARKDOWN)
		resolverProvider.add(new ServiceMonitorMarkdownResolver());
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

}
