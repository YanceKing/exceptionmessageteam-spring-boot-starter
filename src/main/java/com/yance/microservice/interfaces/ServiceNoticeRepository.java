package com.yance.microservice.interfaces;

import com.yance.pojos.servicemonitor.MicroServiceReport;
import com.yance.pojos.servicemonitor.ServiceHealthProblem;
import com.yance.pojos.servicemonitor.ServiceInstanceLackProblem;

import java.util.Set;

public interface ServiceNoticeRepository {

	void addServiceLackProblem(ServiceInstanceLackProblem serviceInstanceLackProblem);

	void addServiceHealthProblem(ServiceHealthProblem serviceHealthProblem);

	void addLackServices(Set<String> serviceName);

	void addLackServices(String... serviceName);

	MicroServiceReport report();
}
