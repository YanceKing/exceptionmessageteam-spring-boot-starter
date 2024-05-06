package com.yance.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearBodyInterceptor implements HandlerInterceptor {

	private CurrentRequetBodyResolver currentRequetBodyResolver;

	private final Log logger = LogFactory.getLog(getClass());

	public ClearBodyInterceptor(CurrentRequetBodyResolver currentRequetBodyResolver) {
		this.currentRequetBodyResolver = currentRequetBodyResolver;
	}

	public ClearBodyInterceptor() {
	}

	/**
	 * @return the currentRequetBodyResolver
	 */
	public CurrentRequetBodyResolver getCurrentRequetBodyResolver() {
		return currentRequetBodyResolver;
	}

	/**
	 * @param currentRequetBodyResolver the currentRequetBodyResolver to set
	 */
	public void setCurrentRequetBodyResolver(CurrentRequetBodyResolver currentRequetBodyResolver) {
		this.currentRequetBodyResolver = currentRequetBodyResolver;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("清除请求体数据");
		currentRequetBodyResolver.remove();
	}

}
