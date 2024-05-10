package com.yance.exceptionhandle.event;

import com.yance.pojos.ExceptionNotice;
import org.springframework.context.ApplicationEvent;

public class ExceptionNoticeEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final ExceptionNotice exceptionNotice;

	private final String blameFor;

	/**
	 * @param source
	 * @param exceptionNotice
	 */
	public ExceptionNoticeEvent(Object source, ExceptionNotice exceptionNotice, String blameFor) {
		super(source);
		this.exceptionNotice = exceptionNotice;
		this.blameFor = blameFor;
	}

	/**
	 * @return the exceptionNotice
	 */
	public ExceptionNotice getExceptionNotice() {
		return exceptionNotice;
	}

	/**
	 * @return the blameFor
	 */
	public String getBlameFor() {
		return blameFor;
	}

}
