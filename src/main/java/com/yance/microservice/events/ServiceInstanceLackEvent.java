package com.yance.microservice.events;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class ServiceInstanceLackEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final String serviceName;

	private final int serviceCount;

	private final Set<String> instanceIds;

	public ServiceInstanceLackEvent(Object source, String serviceName, int serviceCount, Set<String> instanceIds) {
		super(source);
		this.serviceName = serviceName;
		this.serviceCount = serviceCount;
		this.instanceIds = instanceIds;
	}

	public String getServiceName() {
		return serviceName;
	}

	public int getServiceCount() {
		return serviceCount;
	}

	public Set<String> getInstanceIds() {
		return instanceIds;
	}

}
