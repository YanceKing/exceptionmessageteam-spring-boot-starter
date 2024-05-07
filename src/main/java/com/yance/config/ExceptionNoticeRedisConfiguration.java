package com.yance.config;

import com.google.gson.Gson;
import com.yance.exceptionhandle.ExceptionHandler;
import com.yance.properties.ExceptionNoticeProperty;
import com.yance.redis.ExceptionRedisStorageComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 异常通知Redis配置
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
@Configuration
@ConditionalOnClass({ StringRedisTemplate.class })
@ConditionalOnProperty(name = "exceptionnotice.enable-redis-storage", havingValue = "true")
@ConditionalOnMissingBean({ ExceptionRedisStorageComponent.class })
@ConditionalOnBean({ ExceptionHandler.class })
@AutoConfigureAfter({ ExceptionNoticeConfig.class })
public class ExceptionNoticeRedisConfiguration {

	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@Bean
	public ExceptionRedisStorageComponent exceptionRedisStorageComponent(StringRedisTemplate stringRedisTemplate,
			Gson gson, ExceptionHandler exceptionHandler) {
		ExceptionRedisStorageComponent exceptionRedisStorageComponent = new ExceptionRedisStorageComponent(
				exceptionNoticeProperty, stringRedisTemplate, gson);
		exceptionHandler.setExceptionRedisStorageComponent(exceptionRedisStorageComponent);
		return exceptionRedisStorageComponent;
	}

}
