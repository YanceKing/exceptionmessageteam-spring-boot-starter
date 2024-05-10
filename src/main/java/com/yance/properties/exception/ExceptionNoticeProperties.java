package com.yance.properties.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;
import com.yance.properties.enums.ListenType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ConfigurationProperties(prefix = "prometheus.exceptionnotice")
public class ExceptionNoticeProperties {

	/**
	 * 是否开启异常通知
	 */
	private boolean enabled = false;

	/**
	 * 追踪信息的包含的包名
	 */
	private String includedTracePackage;

	/**
	 * 排除的需要统计的异常
	 */
	private List<Class<? extends Exception>> excludeExceptions = new LinkedList<>();

	/**
	 * 当listenType为MVC时，处理请求异常通知时需要的header名称信息
	 */
	private List<String> includeHeaderName = new ArrayList<String>();

	/**
	 * <p>
	 * 通过注解进行监控，目前提供两种方式：
	 * </p>
	 * <ol>
	 * 一种只是普通的监视方法中的异常，主要包含了方法名、方法参数等相关内容；
	 * </ol>
	 * <ol>
	 * 另一种是监视请求出现异常后的通知，额外包含了请求路径、请求参数（param、body）以及想要查询的头信息，对于头信息的过滤参看
	 * </ol>
	 */
	private ListenType listenType = ListenType.COMMON;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getIncludedTracePackage() {
		return includedTracePackage;
	}

	public void setIncludedTracePackage(String includedTracePackage) {
		this.includedTracePackage = includedTracePackage;
	}

	public List<Class<? extends Exception>> getExcludeExceptions() {
		return excludeExceptions;
	}

	public void setExcludeExceptions(List<Class<? extends Exception>> excludeExceptions) {
		this.excludeExceptions = excludeExceptions;
	}

	public List<String> getIncludeHeaderName() {
		return includeHeaderName;
	}

	public void setIncludeHeaderName(List<String> includeHeaderName) {
		this.includeHeaderName = includeHeaderName;
	}

	public ListenType getListenType() {
		return listenType;
	}

	public void setListenType(ListenType listenType) {
		this.listenType = listenType;
	}

}
