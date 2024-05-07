package com.yance.message;

import com.yance.content.ExceptionNotice;

import java.util.Collection;

/**
 * 异常通知 INoticeSendComponent
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public interface INoticeSendComponent {

	public void send(String blamedFor, ExceptionNotice exceptionNotice);

	public Collection<String> getAllBuddies();
}
