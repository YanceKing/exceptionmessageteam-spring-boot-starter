package com.yance.text;

import com.yance.exceptions.PrometheusException;
import com.yance.pojos.PromethuesNotice;
import com.yance.pojos.servicemonitor.ServiceCheckNotice;

public interface ServiceMonitorResolver extends NoticeTextResolver {

	default String resolve(PromethuesNotice notice) {
		if (notice instanceof ServiceCheckNotice) {
			ServiceCheckNotice serviceCheckNotice = (ServiceCheckNotice) notice;
			return serviceMonitorResolve(serviceCheckNotice);
		} else
			throw new PrometheusException("the type of notice is incorrect !");

	}

	String serviceMonitorResolve(ServiceCheckNotice serviceCheckNotice);
}
