package com.yance.microservice.events;

import org.springframework.context.ApplicationListener;
import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.pojos.servicemonitor.ServiceInstanceLackProblem;

import java.util.Set;

public class ServiceInstanceLackEventListener implements ApplicationListener<ServiceInstanceLackEvent> {

	private final ServiceNoticeRepository serviceNoticeRepository;

	public ServiceInstanceLackEventListener(ServiceNoticeRepository serviceNoticeRepository) {
		this.serviceNoticeRepository = serviceNoticeRepository;
	}

	@Override
	public void onApplicationEvent(ServiceInstanceLackEvent event) {
		Set<String> existedInstances = event.getInstanceIds();
		int lackCount = event.getServiceCount() - existedInstances.size();
		if (lackCount > 0 && lackCount == event.getServiceCount())
			serviceNoticeRepository.addLackServices(event.getServiceName());
		else if (lackCount > 0) {
			serviceNoticeRepository.addServiceLackProblem(
					new ServiceInstanceLackProblem(event.getServiceName(), event.getInstanceIds(), lackCount));
		}
	}

}
