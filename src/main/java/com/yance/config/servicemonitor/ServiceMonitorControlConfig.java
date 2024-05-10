package com.yance.config.servicemonitor;
import com.yance.config.annos.ConditionalOnServiceMonitor;
import com.yance.microservice.control.ServiceCheckControl;
import com.yance.microservice.control.ServiceExistControl;
import com.yance.microservice.control.ServiceNoticeControl;
import com.yance.microservice.interfaces.HealthCheckHandler;
import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.notice.NoticeComponentFactory;
import com.yance.properties.PrometheusProperties;
import com.yance.properties.servicemonitor.ServiceMonitorProperties;
import com.yance.text.NoticeTextResolverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
@ConditionalOnServiceMonitor
public class ServiceMonitorControlConfig {

	@Autowired
	private ServiceMonitorProperties serviceMonitorProperties;

	@Autowired
	private TaskScheduler promethuesMicroServiceScheduler;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 
	 * 服务检查控制器
	 * 
	 * @param healthCheckHandler
	 * @return
	 */
	@Bean
	public ServiceCheckControl serviceCheckControl(HealthCheckHandler healthCheckHandler) {
		ServiceCheckControl checkControl = new ServiceCheckControl(promethuesMicroServiceScheduler,
				serviceMonitorProperties, discoveryClient, applicationEventPublisher, healthCheckHandler);
		return checkControl;
	}

	/**
	 * 
	 * 服务通知控制器
	 * 
	 * @param serviceMonitorProperties
	 * @param promethreusNoticeProperties
	 * @param serviceNoticeRepository
	 * @param noticeTextResolverProvider
	 * @param noticeComponentFactory
	 * @return
	 */
	@Bean
	public ServiceNoticeControl serviceNoticeControl(ServiceMonitorProperties serviceMonitorProperties,
													 PrometheusProperties promethreusNoticeProperties, ServiceNoticeRepository serviceNoticeRepository,
													 NoticeTextResolverProvider noticeTextResolverProvider, NoticeComponentFactory noticeComponentFactory) {
		ServiceNoticeControl serviceNoticeControl = new ServiceNoticeControl(serviceMonitorProperties,
				promethreusNoticeProperties, promethuesMicroServiceScheduler, serviceNoticeRepository,
				noticeTextResolverProvider, noticeComponentFactory);
		return serviceNoticeControl;
	}

	/**
	 * 服务存在性控制器
	 * 
	 * @return
	 */
	@Bean
	public ServiceExistControl serviceExistControl() {
		ServiceExistControl serviceExistControl = new ServiceExistControl(promethuesMicroServiceScheduler,
				discoveryClient, applicationEventPublisher, serviceMonitorProperties);
		return serviceExistControl;
	}
}
