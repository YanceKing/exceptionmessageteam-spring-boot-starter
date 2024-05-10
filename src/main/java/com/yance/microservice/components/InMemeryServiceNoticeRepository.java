package com.yance.microservice.components;


import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.pojos.servicemonitor.MicroServiceReport;
import com.yance.pojos.servicemonitor.ServiceHealthProblem;
import com.yance.pojos.servicemonitor.ServiceInstanceLackProblem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InMemeryServiceNoticeRepository implements ServiceNoticeRepository {

	private MicroServiceReport lastReport = null;

	private MicroServiceReport currentReport = new MicroServiceReport();

	@Override
	public void addServiceLackProblem(ServiceInstanceLackProblem serviceInstanceLackProblem) {
		currentReport.putLackInstanceService(serviceInstanceLackProblem.getServiceName(), serviceInstanceLackProblem);
	}

	@Override
	public void addServiceHealthProblem(ServiceHealthProblem serviceHealthProblem) {
		currentReport.putUnHealthyService(serviceHealthProblem.getServiceName(), serviceHealthProblem);
	}

	@Override
	public synchronized MicroServiceReport report() {
		if (currentReport.isNeedReport()) {
			lastReport = currentReport;
			currentReport = new MicroServiceReport();
			return lastReport;
		}
		return currentReport;
	}

	@Override
	public void addLackServices(Set<String> serviceName) {
		currentReport.putLackServices(serviceName);
	}

	@Override
	public void addLackServices(String... serviceName) {
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(serviceName));
		currentReport.putLackServices(set);
	}

}
