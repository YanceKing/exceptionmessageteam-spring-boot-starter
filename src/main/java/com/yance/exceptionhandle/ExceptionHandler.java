package com.yance.exceptionhandle;

import com.yance.exceptionhandle.event.ExceptionNoticeEvent;
import com.yance.exceptions.PrometheusException;
import com.yance.pojos.ExceptionNotice;
import com.yance.pojos.HttpExceptionNotice;
import com.yance.properties.PrometheusProperties;
import com.yance.properties.exception.ExceptionNoticeProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExceptionHandler {

	private final PrometheusProperties prometheusProperties;

	private final ExceptionNoticeProperties exceptionNoticeProperties;

	private final ApplicationEventPublisher applicationEventPublisher;

	private final Log logger = LogFactory.getLog(getClass());

	private final String defaultBlameFor;

	public ExceptionHandler(PrometheusProperties prometheusProperties,
			ExceptionNoticeProperties exceptionNoticeProperties, ApplicationEventPublisher applicationEventPublisher) {
		this.prometheusProperties = prometheusProperties;
		this.exceptionNoticeProperties = exceptionNoticeProperties;
		this.applicationEventPublisher = applicationEventPublisher;
		this.defaultBlameFor = prometheusProperties.getDefaultName();
	}

	private String blameFor(String blameFor) {
		blameFor = StringUtils.isBlank(blameFor) ? defaultBlameFor : blameFor;
		if (blameFor.isBlank())
			throw new PrometheusException("no blame for this error!");
		return blameFor;
	}

	public ExceptionNotice createNotice(String blameFor, Throwable exception) {
		if (containsException(exception))
			return null;
		blameFor = blameFor(blameFor);
		ExceptionNotice exceptionNotice = new ExceptionNotice(exception,
				exceptionNoticeProperties.getIncludedTracePackage(), null, prometheusProperties.getProjectEnviroment(),
				String.format("%s的异常通知", prometheusProperties.getProjectName()));
		exceptionNotice.setProject(prometheusProperties.getProjectName());
		applicationEventPublisher.publishEvent(new ExceptionNoticeEvent(this, exceptionNotice, blameFor));
		return exceptionNotice;

	}

	private boolean containsException(Throwable exception) {
		List<Class<? extends Throwable>> thisEClass = getAllExceptionClazz(exception);
		List<Class<? extends Exception>> list = exceptionNoticeProperties.getExcludeExceptions();
		for (Class<? extends Exception> clazz : list) {
			if (thisEClass.stream().anyMatch(c -> clazz.isAssignableFrom(c)))
				return true;
		}
		return false;
	}

	private List<Class<? extends Throwable>> getAllExceptionClazz(Throwable exception) {
		List<Class<? extends Throwable>> list = new LinkedList<Class<? extends Throwable>>();
		list.add(exception.getClass());
		Throwable cause = exception.getCause();
		while (cause != null) {
			list.add(cause.getClass());
			cause = cause.getCause();
		}
		return list;
	}

	/**
	 * 反射方式获取方法中出现的异常进行的通知
	 * 
	 * @param ex     异常信息
	 * @param method 方法名
	 * @param args   参数信息
	 * @return
	 */
	public ExceptionNotice createNotice(String blameFor, Throwable ex, String method, Object[] args) {
		if (containsException(ex))
			return null;
		blameFor = blameFor(blameFor);
		ExceptionNotice exceptionNotice = new ExceptionNotice(ex, exceptionNoticeProperties.getIncludedTracePackage(),
				args, prometheusProperties.getProjectEnviroment(),
				String.format("%s的异常通知", prometheusProperties.getProjectName()));
		logger.debug("创建异常通知：" + method);
		exceptionNotice.setProject(prometheusProperties.getProjectName());
		applicationEventPublisher.publishEvent(new ExceptionNoticeEvent(this, exceptionNotice, blameFor));
		return exceptionNotice;

	}

	/**
	 * 创建一个http请求异常的通知
	 * 
	 * @param exception
	 * @param url
	 * @param param
	 * @param requesBody
	 * @param headers
	 * @return
	 */
	public HttpExceptionNotice createHttpNotice(String blameFor, Throwable exception, String url,
												Map<String, String> param, String requesBody, Map<String, String> headers) {
		if (containsException(exception))
			return null;
		blameFor = blameFor(blameFor);
		logger.debug("创建异常通知：" + url);
		HttpExceptionNotice exceptionNotice = new HttpExceptionNotice(exception,
				exceptionNoticeProperties.getIncludedTracePackage(), url, param, requesBody, headers,
				prometheusProperties.getProjectEnviroment(),
				String.format("%s的异常通知", prometheusProperties.getProjectName()));
		exceptionNotice.setProject(prometheusProperties.getProjectName());
		applicationEventPublisher.publishEvent(new ExceptionNoticeEvent(this, exceptionNotice, blameFor));
		return exceptionNotice;
	}

}
