package com.yance.config.servicemonitor;

import com.ecwid.consul.v1.ConsulClient;
import com.yance.config.annos.ConditionalOnServiceMonitor;
import com.yance.microservice.components.ConsulHealthCheckHandler;
import com.yance.microservice.interfaces.HealthCheckHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.consul.discovery.ConditionalOnConsulDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 徐晓东
 *
 */
@Configuration
@ConditionalOnServiceMonitor
@ConditionalOnConsulDiscoveryEnabled
public class ConsulHealthCheckHandlerConfig {

	@Bean
	@ConditionalOnMissingBean
	public HealthCheckHandler healthCheckHandler(ConsulClient consulClient) {
		HealthCheckHandler handler = new ConsulHealthCheckHandler(consulClient);
		return handler;
	}
}
