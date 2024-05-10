package com.yance.microservice.events;

import org.springframework.context.ApplicationListener;
import com.yance.microservice.interfaces.ServiceNoticeRepository;

public class ServiceLostEventListener implements ApplicationListener<ServiceLostEvent> {

	private final ServiceNoticeRepository serviceNoticeRepository;

	public ServiceLostEventListener(ServiceNoticeRepository serviceNoticeRepository) {
		super();
		this.serviceNoticeRepository = serviceNoticeRepository;
	}

	@Override
	public void onApplicationEvent(ServiceLostEvent event) {
		serviceNoticeRepository.addLackServices(event.getServiceNames());
	}

}
