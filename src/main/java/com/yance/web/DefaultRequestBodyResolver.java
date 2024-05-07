package com.yance.web;

import com.yance.anno.ExceptionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * 默认请求体解析器
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class DefaultRequestBodyResolver extends RequestBodyAdviceAdapter implements CurrentRequetBodyResolver {

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
		logger.debug("请求体信息：" + bodyStr);
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

}
