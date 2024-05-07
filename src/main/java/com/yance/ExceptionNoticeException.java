package com.yance;

/**
 * 异常通知 运行时异常
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class ExceptionNoticeException extends RuntimeException {

	public ExceptionNoticeException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 9006489175368117913L;

}
