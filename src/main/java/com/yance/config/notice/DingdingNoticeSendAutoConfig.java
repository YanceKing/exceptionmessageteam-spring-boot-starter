package com.yance.config.notice;

import com.yance.config.conditions.PrometheusEnabledCondition;
import com.yance.httpclient.DingdingHttpClient;
import com.yance.notice.DingDingNoticeSendComponent;
import com.yance.properties.notice.PrometheusNoticeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@Conditional(PrometheusEnabledCondition.class)
public class DingdingNoticeSendAutoConfig implements NoticeSendComponentCustomer {

	@Autowired
	private PrometheusNoticeProperties prometheusNoticeProperties;

	@Autowired
	private DingdingHttpClient dingdingHttpClient;

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 1;
	}

	@Override
	public void custom(NoticeSendComponentRegister register) {
		prometheusNoticeProperties.getDingding().forEach((x, y) -> {
			DingDingNoticeSendComponent dingDingNoticeSendComponent = new DingDingNoticeSendComponent(
					dingdingHttpClient, y, prometheusNoticeProperties.getDingdingTextType());
			register.add(x, dingDingNoticeSendComponent);
		});
	}

}
