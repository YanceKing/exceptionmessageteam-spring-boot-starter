package com.yance.microservice.interfaces;

import org.springframework.cloud.client.ServiceInstance;
import com.yance.properties.servicemonitor.ServiceCheck;

@FunctionalInterface
public interface HealthCheckHandler {

	/**
	 * 
	 * 健康检查处理
	 * 
	 * @param serviceInstance
	 * @param serviceCheck
	 * @return
	 */
	public boolean isHealthy(ServiceInstance serviceInstance, ServiceCheck serviceCheck);

}
