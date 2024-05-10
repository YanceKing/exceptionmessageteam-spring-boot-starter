package com.yance.microservice.events;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

public class ServiceLostEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final Set<String> serviceNames;

	public ServiceLostEvent(Object source, Set<String> serviceNames) {
		super(source);
		this.serviceNames = serviceNames;
	}

	public Set<String> getServiceNames() {
		return serviceNames;
	}

}
