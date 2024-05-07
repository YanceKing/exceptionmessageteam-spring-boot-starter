package com.yance.web;

import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

/**
 * 请求body解析器
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public interface CurrentRequetBodyResolver extends RequestBodyAdvice {

	default String getRequestBody() {
		return "";
	}

	void remove();
}
