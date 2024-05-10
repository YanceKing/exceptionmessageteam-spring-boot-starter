package com.yance.microservice.events;

import org.springframework.context.ApplicationListener;
import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.pojos.servicemonitor.ServiceHealthProblem;

public class ServiceInstanceUnhealthyEventListener implements ApplicationListener<ServiceInstanceUnhealthyEvent> {

	private final ServiceNoticeRepository serviceNoticeRepository;

//	private final Log logger = LogFactory.getLog(ServiceInstanceUnhealthyEvent.class);

	public ServiceInstanceUnhealthyEventListener(ServiceNoticeRepository serviceNoticeRepository) {
		super();
		this.serviceNoticeRepository = serviceNoticeRepository;
	}

	@Override
	public void onApplicationEvent(ServiceInstanceUnhealthyEvent event) {
		serviceNoticeRepository.addServiceHealthProblem(new ServiceHealthProblem(event));

	}

}
