package com.yance.config.notice;

import org.springframework.core.Ordered;

public interface NoticeSendComponentCustomer extends Ordered {

	void custom(NoticeSendComponentRegister register);
}
