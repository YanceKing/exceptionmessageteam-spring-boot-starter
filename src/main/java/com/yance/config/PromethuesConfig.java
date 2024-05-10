package com.yance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yance.config.conditions.PrometheusEnabledCondition;
import com.yance.config.notice.NoticeSendComponentRegister;
import com.yance.config.notice.NoticeTextResolverCustomer;
import com.yance.exceptionhandle.components.InMemeryExceptionStatisticsRepository;
import com.yance.exceptionhandle.interfaces.NoticeStatisticsRepository;
import com.yance.httpclient.DefaultDingdingHttpClient;
import com.yance.httpclient.DingdingHttpClient;
import com.yance.properties.PrometheusProperties;
import com.yance.text.NoticeTextResolver;
import com.yance.text.NoticeTextResolverProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Conditional(PrometheusEnabledCondition.class)
@EnableConfigurationProperties(PrometheusProperties.class)
public class PromethuesConfig {

	@Bean
	public NoticeSendComponentRegister noticeSendComponentRegister() {
		return new NoticeSendComponentRegister();
	}

	@Bean
	public NoticeTextResolverProvider noticeTextResolverProvider(List<NoticeTextResolver> list,
																 List<NoticeTextResolverCustomer> resolverCustomers) {
		NoticeTextResolverProvider provider = new NoticeTextResolverProvider(list);
		resolverCustomers.forEach(x -> x.custom(provider));
		return provider;
	}

	@Bean
	@ConditionalOnMissingBean
	public DingdingHttpClient dingdingHttpClient(ObjectMapper objectMapper) {
		DingdingHttpClient dingdingHttpClient = new DefaultDingdingHttpClient(objectMapper);
		return dingdingHttpClient;
	}

	@Bean
	@ConditionalOnMissingBean
	public NoticeStatisticsRepository exceptionNoticeStatisticsRepository() {
		NoticeStatisticsRepository repository = new InMemeryExceptionStatisticsRepository();
		return repository;
	}
}
