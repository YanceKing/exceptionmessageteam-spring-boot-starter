package com.yance.config;

import com.yance.exceptionhandle.ExceptionHandler;
import com.yance.properties.ExceptionNoticeProperty;
import com.yance.web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * 异常通知监听Web配置
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
@Configuration
@ConditionalOnClass({ WebMvcConfigurer.class, RequestBodyAdvice.class, RequestMappingHandlerAdapter.class })
@ConditionalOnProperty(name = "exceptionnotice.listen-type", havingValue = "web-mvc")
@ConditionalOnBean({ ExceptionHandler.class })
public class ExceptionNoticeWebListenConfig implements WebMvcConfigurer, WebMvcRegistrations {

	@Autowired
	private ExceptionHandler exceptionHandler;
	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		resolvers.add(0, exceptionNoticeResolver());
	}

	@Bean
	public ExceptionNoticeResolver exceptionNoticeResolver() {
		ExceptionNoticeResolver exceptionNoticeResolver = new ExceptionNoticeResolver(exceptionHandler,
				currentRequetBodyResolver(), currentRequestHeaderResolver(), exceptionNoticeProperty);
		return exceptionNoticeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(clearBodyInterceptor());
	}

	@Bean
	public ClearBodyInterceptor clearBodyInterceptor() {
		ClearBodyInterceptor bodyInterceptor = new ClearBodyInterceptor(currentRequetBodyResolver());
		return bodyInterceptor;

	}

	@Bean
	@ConditionalOnMissingBean(value = CurrentRequestHeaderResolver.class)
	public CurrentRequestHeaderResolver currentRequestHeaderResolver() {
		return new DefaultRequestHeaderResolver();
	}

	@Bean
	public CurrentRequetBodyResolver currentRequetBodyResolver() {
		return new DefaultRequestBodyResolver();
	}

	@Override
	public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
		adapter.setRequestBodyAdvice(Arrays.asList(currentRequetBodyResolver()));
		return adapter;
	}

}
