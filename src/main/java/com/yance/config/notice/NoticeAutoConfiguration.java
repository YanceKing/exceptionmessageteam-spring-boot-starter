package com.yance.config.notice;

import com.yance.config.conditions.PrometheusEnabledCondition;
import com.yance.notice.DefaultNoticCompoenntFactory;
import com.yance.notice.NoticeComponentFactory;
import com.yance.properties.notice.PrometheusNoticeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties({ PrometheusNoticeProperties.class })
@Conditional(PrometheusEnabledCondition.class)
public class NoticeAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public NoticeComponentFactory noticeComponentFactory(List<NoticeSendComponentCustomer> customers,
														 NoticeSendComponentRegister noticeSendComponentRegister) {
		customers.forEach(customer -> customer.custom(noticeSendComponentRegister));
		DefaultNoticCompoenntFactory compoenntFactory = new DefaultNoticCompoenntFactory(noticeSendComponentRegister);
		return compoenntFactory;
	}
}
