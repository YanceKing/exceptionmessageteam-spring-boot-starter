package com.yance.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import com.yance.anno.ExceptionListener;

import java.io.IOException;
import java.lang.reflect.Type;

public class DefaultRequestBodyResolver implements CurrentRequetBodyResolver {

	private final ThreadLocal<String> currentRequestBodyInfo = ThreadLocal.withInitial(() -> "");

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		StringBuilder stringBuilder = new StringBuilder(body.toString());
		String bodyStr = "";
		if (stringBuilder.length() > 500)
			bodyStr = stringBuilder.substring(0, 500) + "...";
		else
			bodyStr = stringBuilder.toString();
		logger.debug("请求体信息：" + body);
		currentRequestBodyInfo.set(bodyStr);
		return body;
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return methodParameter.hasMethodAnnotation(ExceptionListener.class)
				|| methodParameter.getContainingClass().isAnnotationPresent(ExceptionListener.class);
	}

	@Override
	public String getRequestBody() {
		return currentRequestBodyInfo.get();
	}

	public void remove() {
		currentRequestBodyInfo.remove();
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		return inputMessage;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return body;
	}

}
