package com.yance.microservice.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.notice.NoticeComponentFactory;
import com.yance.pojos.servicemonitor.MicroServiceReport;
import com.yance.pojos.servicemonitor.ServiceCheckNotice;
import com.yance.properties.PrometheusProperties;
import com.yance.text.NoticeTextResolverProvider;

public class ServiceNoticeTask implements Runnable {

	private final NoticeComponentFactory noticeComponentFactory;

	private final NoticeTextResolverProvider noticeTextResolverProvider;

	private final PrometheusProperties promethreusNoticeProperties;

	private final Log logger = LogFactory.getLog(ServiceNoticeTask.class);

	private final ServiceNoticeRepository serviceNoticeRepository;

	public ServiceNoticeTask(NoticeComponentFactory noticeComponentFactory,
			PrometheusProperties promethreusNoticeProperties, ServiceNoticeRepository serviceNoticeRepository,
			NoticeTextResolverProvider noticeTextResolverProvider) {
		this.noticeComponentFactory = noticeComponentFactory;
		this.noticeTextResolverProvider = noticeTextResolverProvider;
		this.promethreusNoticeProperties = promethreusNoticeProperties;
		this.serviceNoticeRepository = serviceNoticeRepository;
	}

	@Override
	public void run() {
		MicroServiceReport microServiceNotice = serviceNoticeRepository.report();
		if (microServiceNotice.isNeedReport()) {
			int problemCount = microServiceNotice.totalProblemCount();
			logger.debug("prepare for notice: \n " + microServiceNotice);
			ServiceCheckNotice serviceCheckNotice = new ServiceCheckNotice(microServiceNotice,
					promethreusNoticeProperties.getProjectEnviroment(), "服务监控通知");
			serviceCheckNotice.setProblemServiceCount(problemCount);
			noticeComponentFactory.get(promethreusNoticeProperties.getDefaultName()).forEach(
					x -> x.send(serviceCheckNotice, noticeTextResolverProvider.get(ServiceCheckNotice.class, x)));
		}
	}
}
