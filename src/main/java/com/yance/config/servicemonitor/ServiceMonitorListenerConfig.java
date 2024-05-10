package com.yance.config.servicemonitor;

import com.yance.config.annos.ConditionalOnServiceMonitor;
import com.yance.microservice.control.ServiceCheckControl;
import com.yance.microservice.events.ServiceDiscoveredListener;
import com.yance.microservice.events.ServiceInstanceLackEventListener;
import com.yance.microservice.events.ServiceInstanceUnhealthyEventListener;
import com.yance.microservice.events.ServiceLostEventListener;
import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.properties.servicemonitor.ServiceMonitorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnServiceMonitor
public class ServiceMonitorListenerConfig {

	@Autowired
	private ServiceMonitorProperties serviceMonitorProperties;

	@Bean
	public ServiceDiscoveredListener serviceExistedListener(ServiceCheckControl serviceCheckControl,
															DiscoveryClient discoveryClient, ApplicationEventPublisher publisher) {
		ServiceDiscoveredListener existedListener = new ServiceDiscoveredListener(serviceCheckControl,
				serviceMonitorProperties.getMonitorServices(), discoveryClient, publisher);
		return existedListener;
	}

	@Bean
	public ServiceInstanceLackEventListener serviceInstanceLackEventListener(
			ServiceNoticeRepository serviceNoticeRepository) {
		return new ServiceInstanceLackEventListener(serviceNoticeRepository);
	}

	@Bean
	public ServiceInstanceUnhealthyEventListener serviceInstanceUnhealthyEventListener(
			ServiceNoticeRepository serviceNoticeRepository) {
		return new ServiceInstanceUnhealthyEventListener(serviceNoticeRepository);
	}

	@Bean
	public ServiceLostEventListener serviceLostEventListener(ServiceNoticeRepository serviceNoticeRepository) {
		ServiceLostEventListener serviceLostEventListener = new ServiceLostEventListener(serviceNoticeRepository);
		return serviceLostEventListener;
	}
}
