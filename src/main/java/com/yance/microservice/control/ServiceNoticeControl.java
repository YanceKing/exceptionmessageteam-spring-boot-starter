package com.yance.microservice.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import com.yance.microservice.interfaces.ServiceNoticeRepository;
import com.yance.microservice.task.ServiceNoticeTask;
import com.yance.notice.NoticeComponentFactory;
import com.yance.properties.PrometheusProperties;
import com.yance.properties.servicemonitor.ServiceMonitorProperties;
import com.yance.text.NoticeTextResolverProvider;

import java.util.concurrent.ScheduledFuture;

public class ServiceNoticeControl implements SmartInitializingSingleton, DisposableBean {

	private final Log logger = LogFactory.getLog(ServiceNoticeControl.class);

	private final ServiceMonitorProperties serviceMonitorProperties;

	private final PrometheusProperties promethreusNoticeProperties;

	private final TaskScheduler taskScheduler;

	private final ServiceNoticeRepository serviceNoticeRepository;

	private final NoticeComponentFactory noticeComponentFactory;

	private final NoticeTextResolverProvider noticeTextResolverProvider;

	private ScheduledFuture<?> result;

	/**
	 * @param serviceMonitorProperties
	 * @param promethreusNoticeProperties
	 * @param taskScheduler
	 * @param serviceNoticeRepository
	 * @param noticeTextResolverProvider
	 * @param noticeComponentFactory
	 */
	public ServiceNoticeControl(ServiceMonitorProperties serviceMonitorProperties,
			PrometheusProperties promethreusNoticeProperties, TaskScheduler taskScheduler,
			ServiceNoticeRepository serviceNoticeRepository, NoticeTextResolverProvider noticeTextResolverProvider,
			NoticeComponentFactory noticeComponentFactory) {
		this.serviceMonitorProperties = serviceMonitorProperties;
		this.promethreusNoticeProperties = promethreusNoticeProperties;
		this.taskScheduler = taskScheduler;
		this.serviceNoticeRepository = serviceNoticeRepository;
		this.noticeComponentFactory = noticeComponentFactory;
		this.noticeTextResolverProvider = noticeTextResolverProvider;
	}

	public ServiceMonitorProperties getServiceMonitorProperties() {
		return serviceMonitorProperties;
	}

	/**
	 * @return the result
	 */
	public ScheduledFuture<?> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ScheduledFuture<?> result) {
		this.result = result;
	}

	/**
	 * @return the taskScheduler
	 */
	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	@Override
	public void destroy() throws Exception {
		result.cancel(false);
	}

	@Override
	public void afterSingletonsInstantiated() {
		logger.debug("开启通知任务");
		ServiceNoticeTask serviceNoticeTask = new ServiceNoticeTask(noticeComponentFactory, promethreusNoticeProperties,
				serviceNoticeRepository, noticeTextResolverProvider);
		PeriodicTrigger trigger = new PeriodicTrigger(serviceMonitorProperties.getServiceCheckNoticeInterval());
		trigger.setInitialDelay(serviceMonitorProperties.getServiceNoticeInitialDelay());
		result = taskScheduler.schedule(serviceNoticeTask, trigger);
	}
}
