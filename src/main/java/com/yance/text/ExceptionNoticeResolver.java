package com.yance.text;

import com.yance.exceptions.PrometheusException;
import com.yance.pojos.ExceptionNotice;
import com.yance.pojos.PromethuesNotice;

public interface ExceptionNoticeResolver extends NoticeTextResolver {

	default String resolve(PromethuesNotice notice) {
		if (notice instanceof ExceptionNotice) {
			ExceptionNotice exceptionNotice = (ExceptionNotice) notice;
			return exceptionNoticeResolve(exceptionNotice);
		} else
			throw new PrometheusException("the type of notice is incorrect !");

	}

	String exceptionNoticeResolve(ExceptionNotice exceptionNotice);
}
