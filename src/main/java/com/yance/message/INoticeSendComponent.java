package com.yance.message;

import com.yance.content.ExceptionNotice;

import java.util.Collection;

public interface INoticeSendComponent {

	public void send(String blamedFor, ExceptionNotice exceptionNotice);

	public Collection<String> getAllBuddies();
}
